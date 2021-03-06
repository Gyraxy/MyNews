package com.duboscq.nicolas.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.models.Articles;
import com.duboscq.nicolas.mynews.utils.DateUtility;

/**
 * Created by Nicolas DUBOSCQ on 24/07/2018
 */
public class ArticleViewHolder extends RecyclerView.ViewHolder {

    public TextView article_summary_txt;
    public TextView article_title_txt;
    public TextView article_date_txt;
    public ImageView article_picture_img;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        article_summary_txt = itemView.findViewById(R.id.recycler_view_txt_article_summary);
        article_title_txt = itemView.findViewById(R.id.recycler_view_txt_article_title);
        article_date_txt = itemView.findViewById(R.id.recycler_view_txt_article_date);
        article_picture_img = itemView.findViewById(R.id.recycler_view_imv_article_image);
    }

    public void updateArticleinfo(Articles articles,RequestManager glide) {
        article_summary_txt.setText(articles.getTitle());
        article_date_txt.setText(DateUtility.convertingDate(articles.getPublishedDate()));
        if(articles.getMultimedia() != null && articles.getMultimedia().size() > 0) {
            glide.load(articles.getMultimedia().get(0).getUrl()).into(article_picture_img);
        }

        if(articles.getMedia() != null && articles.getMedia().size() > 0){
            glide.load(articles.getMedia().get(0).getMetaMedia().get(0).getUrl()).into(article_picture_img);
        }

        if(articles.getSubsection() == "" || articles.getSubsection() == null) {
            article_title_txt.setText(articles.getSection());
        } else article_title_txt.setText(articles.getSection()+">"+articles.getSubsection());
    }
}
