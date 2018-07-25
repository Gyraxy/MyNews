package com.duboscq.nicolas.mynews.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.ArticleRecyclerViewAdapter;

import butterknife.ButterKnife;

/**
 * Created by DUBOSCQ Nicolas
 */

public class CustomNewsFragment extends Fragment {


    public CustomNewsFragment(){ }

    public static CustomNewsFragment newInstance() {

        return (new CustomNewsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);
        RecyclerView recyclerView = view.findViewById(R.id.article_recycler_view);
        ArticleRecyclerViewAdapter adapter = new ArticleRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

}