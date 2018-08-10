package com.duboscq.nicolas.mynews.controllers.fragments;

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
import com.duboscq.nicolas.mynews.models.Articles;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.utils.APIInterface;
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

public class MostPopularFragment extends Fragment {

    @BindView(R.id.article_swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    List<Articles> article_most_popular_list;
    ArticleRecyclerViewAdapter adapter;

    public MostPopularFragment(){ }

    public static MostPopularFragment newInstance() {

        return (new MostPopularFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        articleCall();
        //configureSwipeRefreshLayout();
        return view;
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
    }

    private void configureRecyclerView (){
        RecyclerView recyclerView = getView().findViewById(R.id.article_recycler_view);
        adapter = new ArticleRecyclerViewAdapter(getContext(),article_most_popular_list, Glide.with(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void articleCall (){
        APIInterface apiInterface = RetrofitUtility.getInstance().create(APIInterface.class);
        Call<GeneralInfo> call = apiInterface.getMostPopular();
        call.enqueue(new Callback<GeneralInfo>() {
            @Override
            public void onResponse(Call<GeneralInfo> call, Response<GeneralInfo> response) {
                article_most_popular_list = response.body().getResults();
                configureRecyclerView();
            }

            @Override
            public void onFailure(Call<GeneralInfo> call, Throwable t) {

            }
        });
    }
}
