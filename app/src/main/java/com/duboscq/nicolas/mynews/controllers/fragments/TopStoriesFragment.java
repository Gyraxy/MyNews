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
import com.duboscq.nicolas.mynews.controllers.activities.ArticleWebViewActivity;
import com.duboscq.nicolas.mynews.models.Articles;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.utils.APIInterface;
import com.duboscq.nicolas.mynews.utils.APIStreams;
import com.duboscq.nicolas.mynews.utils.ItemClickSupport;
import com.duboscq.nicolas.mynews.utils.RetrofitUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DUBOSCQ Nicolas
 */

public class TopStoriesFragment extends Fragment {

    //FOR DESIGN
    @BindView(R.id.article_swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.article_recycler_view) RecyclerView recyclerView;

    //FOR DATA
    List<Articles> article_top_stories_list;
    ArticleRecyclerViewAdapter adapter;
    private Disposable disposable;
    String NETWORK = "NETWORK";

    public TopStoriesFragment() {
    }

    public static TopStoriesFragment newInstance() {

        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        configureRecyclerView();
        configureAndShowArticleHTTP();
        configureSwipeRefreshLayout();
        configureOnClickRecyclerView();
        return view;
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                configureAndShowArticleHTTP();
            }
        });
    }

    private void configureRecyclerView() {
        // 3.1 - Reset list
        this.article_top_stories_list = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.adapter = new ArticleRecyclerViewAdapter(this.article_top_stories_list,Glide.with(this));
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_articles)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent i = new Intent(getActivity(), ArticleWebViewActivity.class);
                        i.putExtra("article_url", article_top_stories_list.get(position).getUrl());
                        startActivity(i);
                    }
                });
    }

    public void configureAndShowArticleHTTP() {
        disposable = APIStreams.getTopstoriesArticles().subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i(NETWORK, "TopStoriesFragment : On Next");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(NETWORK, "TopStoriesFragment : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i(NETWORK, "TopStoriesFragment : On Complete !!");
            }
        });
    }

    private void updateArticles(GeneralInfo generalInfo){
        swipeRefreshLayout.setRefreshing(false);
        article_top_stories_list.addAll(generalInfo.getResults());
        adapter.notifyDataSetChanged();
    }
}
