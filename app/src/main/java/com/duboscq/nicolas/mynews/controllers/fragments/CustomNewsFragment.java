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
import com.duboscq.nicolas.mynews.adapters.DocsRecyclerViewAdapter;
import com.duboscq.nicolas.mynews.controllers.activities.ArticleWebViewActivity;
import com.duboscq.nicolas.mynews.models.Docs;
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

/**
 * Created by DUBOSCQ Nicolas
 */

public class CustomNewsFragment extends Fragment {

    //FOR DESIGN
    @BindView(R.id.article_swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.article_recycler_view) RecyclerView recyclerView;

    //FOR DATA
    List<Docs> docs;
    DocsRecyclerViewAdapter adapter;
    String section = "sports";

    public CustomNewsFragment(){ }

    public static CustomNewsFragment newInstance() {

        return (new CustomNewsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        configureAndShowDocs();
        configureSwipeRefreshLayout();
        return view;
    }
    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                configureAndShowDocs();
            }
        });
    }

    private void configureRecyclerView (){
        adapter = new DocsRecyclerViewAdapter(getContext(),docs, Glide.with(this),section);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    private void configureAndShowDocs (){
        APIInterface apiInterface = RetrofitUtility.getInstance().create(APIInterface.class);
        Call<GeneralInfo> call = apiInterface.getCustom(section);
        call.enqueue(new Callback<GeneralInfo>() {
            @Override
            public void onResponse(Call<GeneralInfo> call, Response<GeneralInfo> response) {
                docs = response.body().getResponse().getDocs();
                configureRecyclerView();
                configureOnClickRecyclerView();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GeneralInfo> call, Throwable t) {
            }
        });
    }
}