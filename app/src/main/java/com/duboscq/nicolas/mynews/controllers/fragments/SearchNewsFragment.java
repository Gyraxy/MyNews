package com.duboscq.nicolas.mynews.controllers.fragments;

/**
 * Created by Nicolas DUBOSCQ on 24/08/2018
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.DocsRecyclerViewAdapter;
import com.duboscq.nicolas.mynews.controllers.activities.ArticleWebViewActivity;
import com.duboscq.nicolas.mynews.models.Docs;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.utils.APIStreams;
import com.duboscq.nicolas.mynews.utils.ItemClickSupport;
import com.duboscq.nicolas.mynews.utils.SharedPreferencesUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class SearchNewsFragment extends Fragment {
    //FOR DESIGN
    @BindView(R.id.article_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.article_recycler_view)
    RecyclerView recyclerView;

    //FOR DATA
    List<Docs> docs;
    DocsRecyclerViewAdapter adapter;
    String begin_date,end_date;
    String search_section,search_query;
    String NETWORK = "NETWORK",SEARCH_SECTION = "SEARCH_SECTION",SEARCH_BEGIN_DATE = "SEARCH_BEGIN_DATE",SEARCH_END_DATE = "SEARCH_END_DATE",SEARCH_QUERY = "SEARCH_QUERY";
    private Disposable disposable;

    public SearchNewsFragment(){ }

    public static SearchNewsFragment newInstance() {
        return (new SearchNewsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSavedSearchParameters();
        if (search_section != null){
            swipeRefreshLayout.setEnabled(true);
            configureRecyclerView();
            if (begin_date == null && end_date == null){
                configureAndShowArticleHTTPWithoutDate();
            } else if (begin_date != null && end_date != null){
                configureAndShowArticleHTTP();
            } else if (begin_date == null && end_date != null){
                configureAndShowArticleHTTPWithoutBeginDate();
            } else if (begin_date != null && end_date == null){
                configureAndShowArticleHTTPWithoutEndDate();
            }
            configureSwipeRefreshLayout();
            configureOnClickRecyclerView();
        }
        else {
            swipeRefreshLayout.setEnabled(false);
        }
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSavedSearchParameters();
                if (begin_date==null && end_date==null){
                    configureAndShowArticleHTTPWithoutDate();
                } else if (begin_date==null && end_date!=null) {
                    configureAndShowArticleHTTPWithoutBeginDate();
                } else if (begin_date!=null && end_date==null){
                    configureAndShowArticleHTTPWithoutEndDate();
                } else if (begin_date!=null && end_date!=null){
                    configureAndShowArticleHTTP();
                }
            }
        });
    }

    private void configureRecyclerView (){
        this.docs = new ArrayList<>();
        this.adapter = new DocsRecyclerViewAdapter(this.docs,Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_articles)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent i = new Intent(getActivity(),ArticleWebViewActivity.class);
                        i.putExtra("article_url",docs.get(position).getWebUrl());
                        startActivity(i);
                    }
                });
    }

    private void configureAndShowArticleHTTP() {
        getSavedSearchParameters();
        disposable = APIStreams.getSearchDocs("\""+search_query+"\""+" AND section_name.contains:("+search_section+")",begin_date,end_date).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i(NETWORK, "SearchNewsFragment : Search Fragment : On Next");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(NETWORK, "SearchNewsFragment : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i(NETWORK, "SearchNewsFragment : On Complete !!");
            }
        });
    }

    private void configureAndShowArticleHTTPWithoutDate(){
        disposable = APIStreams.getSearchDocsWithoutDate("\""+search_query+"\""+" AND section_name.contains:("+search_section+")").subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i(NETWORK, "SearchNewsFragment : On Next HTTP Request without date");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(NETWORK, "SearchNewsFragment : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i(NETWORK, "SearchNewsFragment : On Complete HTTP Request without date !!");
            }
        });
    }

    private void configureAndShowArticleHTTPWithoutBeginDate(){
        disposable = APIStreams.getSearchDocsWithoutBeginDate("\""+search_query+"\""+" AND section_name.contains:("+search_section+")",end_date).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i(NETWORK, "SearchNewsFragment : On Next HTTP Request without Begin date");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(NETWORK, "SearchNewsFragment : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i(NETWORK, "SearchNewsFragment : On Complete HTTP Request without Begin date");
            }
        });
    }

    private void configureAndShowArticleHTTPWithoutEndDate(){
        disposable = APIStreams.getSearchDocsWithoutEndDate("\""+search_query+"\""+" AND section_name.contains:("+search_section+")",begin_date).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i(NETWORK, "SearchNewsFragment : On Next HTTP Request without End date");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(NETWORK, "SearchNewsFragment : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i(NETWORK, "SearchNewsFragment : On Complete HTTP Request without End date");
            }
        });
    }

    private void updateArticles(GeneralInfo generalInfo){
        swipeRefreshLayout.setRefreshing(false);
        docs.clear();
        docs.addAll(generalInfo.getResponse().getDocs());
        adapter.notifyDataSetChanged();
    }

    private void getSavedSearchParameters(){
        search_section= SharedPreferencesUtility.getString(getContext(),SEARCH_SECTION);
        begin_date = SharedPreferencesUtility.getString(getContext(),SEARCH_BEGIN_DATE);
        end_date = SharedPreferencesUtility.getString(getContext(),SEARCH_END_DATE);
        search_query = SharedPreferencesUtility.getString(getContext(),SEARCH_QUERY);
    }
}