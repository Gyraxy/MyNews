package com.duboscq.nicolas.mynews.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.ArticleRecyclerViewAdapter;
import com.duboscq.nicolas.mynews.controllers.activities.ArticleWebViewActivity;
import com.duboscq.nicolas.mynews.models.Articles;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.utils.APIInterface;
import com.duboscq.nicolas.mynews.utils.ItemClickSupport;
import com.duboscq.nicolas.mynews.utils.RetrofitUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DUBOSCQ Nicolas
 */

public class TopStoriesFragment extends Fragment {

    @BindView(R.id.article_swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    List<Articles> article_top_stories_list;
    ArticleRecyclerViewAdapter adapter;

    public TopStoriesFragment() {
    }

    public static TopStoriesFragment newInstance() {

        return (new TopStoriesFragment());
    }

    @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_articles, container, false);
            ButterKnife.bind(this, view);
            configureAndShowArticle();
            //configureSwipeRefreshLayout();
            return view;
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                configureAndShowArticle();
            }
        });
    }

    private void configureRecyclerView (){
        RecyclerView recyclerView = getView().findViewById(R.id.article_recycler_view);
        adapter = new ArticleRecyclerViewAdapter(getContext(),article_top_stories_list, Glide.with(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void configureOnClickRecyclerView(){
        RecyclerView recyclerView = getView().findViewById(R.id.article_recycler_view);
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_articles)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent i = new Intent(getActivity(),ArticleWebViewActivity.class);
                        i.putExtra("article_url",article_top_stories_list.get(position).getUrl());
                        startActivity(i);
                    }
                });
    }

    private void configureAndShowArticle (){
        APIInterface apiInterface = RetrofitUtility.getInstance().create(APIInterface.class);
        Call<GeneralInfo> call = apiInterface.getTopStories();
        call.enqueue(new Callback<GeneralInfo>() {
            @Override
            public void onResponse(Call<GeneralInfo> call, Response<GeneralInfo> response) {
                article_top_stories_list = response.body().getResults();
                configureRecyclerView();
                configureOnClickRecyclerView();
            }

            @Override
            public void onFailure(Call<GeneralInfo> call, Throwable t) {

            }
        });
    }
}
