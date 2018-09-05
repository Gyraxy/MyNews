package com.duboscq.nicolas.mynews.controllers.fragments;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.ArticleRecyclerViewAdapter;
import com.duboscq.nicolas.mynews.adapters.DocsRecyclerViewAdapter;
import com.duboscq.nicolas.mynews.controllers.activities.ArticleWebViewActivity;
import com.duboscq.nicolas.mynews.models.Docs;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.utils.APIInterface;
import com.duboscq.nicolas.mynews.utils.APIStreams;
import com.duboscq.nicolas.mynews.utils.ItemClickSupport;
import com.duboscq.nicolas.mynews.utils.RetrofitUtility;
import com.duboscq.nicolas.mynews.utils.SharedPreferencesUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUBOSCQ Nicolas
 */

public class CustomNewsFragment extends Fragment {

    //FOR DESIGN
    @BindView(R.id.article_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.article_recycler_view)
    RecyclerView recyclerView;

    //FOR DATA
    List<Docs> docs;
    DocsRecyclerViewAdapter adapter;
    String section_custom;
    private Disposable disposable;

    public CustomNewsFragment(){ }

    public static CustomNewsFragment newInstance() {
        return (new CustomNewsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        section_custom = SharedPreferencesUtility.getString(getContext(),"WEEKLY_SECTION_NAME");
        if (section_custom != null){
            configureRecyclerView();
            configureAndShowArticleHTTP();
            configureSwipeRefreshLayout();
            configureOnClickRecyclerView();
        }
        return view;
    }

    public void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                configureAndShowArticleHTTP();
            }
        });
    }

    private void configureRecyclerView() {
        this.docs = new ArrayList<>();
        this.adapter = new DocsRecyclerViewAdapter(this.docs,Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_articles)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent i = new Intent(getActivity(), ArticleWebViewActivity.class);
                        i.putExtra("article_url", docs.get(position).getWebUrl());
                        startActivity(i);
                    }
                });
    }

    public void configureAndShowArticleHTTP() {
        section_custom = SharedPreferencesUtility.getString(getContext(),"WEEKLY_SECTION_NAME");
        disposable = APIStreams.getWeeklyArticles("section_name:(\""+section_custom+"\")").subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.e("TAG", "On Next");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete !!");
            }
        });
    }

    private void updateArticles(GeneralInfo generalInfo){
        swipeRefreshLayout.setRefreshing(false);
        docs.clear();
        docs.addAll(generalInfo.getResponse().getDocs());
        adapter.notifyDataSetChanged();
    }
}