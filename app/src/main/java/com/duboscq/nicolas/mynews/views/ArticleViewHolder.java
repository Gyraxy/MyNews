package com.duboscq.nicolas.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duboscq.nicolas.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nicolas DUBOSCQ on 24/07/2018
 */
public class ArticleViewHolder extends RecyclerView.ViewHolder {

    public TextView article_title_txt;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        article_title_txt= itemView.findViewById(R.id.recycler_view_txt_article_title);
    }
}
