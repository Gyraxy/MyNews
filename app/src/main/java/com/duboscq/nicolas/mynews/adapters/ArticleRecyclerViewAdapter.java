package com.duboscq.nicolas.mynews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.models.Articles;
import com.duboscq.nicolas.mynews.views.ArticleViewHolder;

import java.util.List;


/**
 * Created by Nicolas DUBOSCQ on 24/07/2018
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<Articles> articles;
    private RequestManager glide;

    public ArticleRecyclerViewAdapter(List<Articles> articles, RequestManager glide) {
        this.articles = articles;
        this.glide = glide;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.article_view_summary, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder ArticleViewHolder, int position) {
            ArticleViewHolder.updateArticleinfo(this.articles.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
