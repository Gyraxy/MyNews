package com.duboscq.nicolas.mynews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.views.ArticleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas DUBOSCQ on 24/07/2018
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private final ArrayList<String> list;
    private Context mContext;

    public ArticleRecyclerViewAdapter(Context mContext) {
        list = new ArrayList<String>();
        list.add("Article 1");
        list.add("Article 2");
        list.add("Article 3");
        list.add("Article 4");
        list.add("Article 5");
        list.add("Article 6");
        this.mContext = mContext;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.article_view, parent, false);
        ArticleViewHolder vHolder = new ArticleViewHolder(view);
        return vHolder;
    }


    @Override
    public void onBindViewHolder(ArticleViewHolder ArticleViewHolder,int position) {
        TextView textView = ArticleViewHolder.article_title_txt;
        textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
