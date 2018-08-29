package com.duboscq.nicolas.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.models.Docs;
import com.duboscq.nicolas.mynews.utils.DateUtility;

/**
 * Created by Nicolas DUBOSCQ on 19/08/2018
 */
public class DocsViewHolder extends RecyclerView.ViewHolder {

    public TextView article_summary_txt;
    public TextView article_title_txt;
    public TextView article_date_txt;
    public ImageView article_picture_img;

    public DocsViewHolder(View itemView) {
        super(itemView);
        article_summary_txt = itemView.findViewById(R.id.recycler_view_txt_article_summary);
        article_title_txt = itemView.findViewById(R.id.recycler_view_txt_article_title);
        article_date_txt = itemView.findViewById(R.id.recycler_view_txt_article_date);
        article_picture_img = itemView.findViewById(R.id.recycler_view_imv_article_image);
    }

    public void updateDocsinfo(Docs docs, RequestManager glide) {
        article_summary_txt.setText(docs.getHeadline().getMain());

        if (docs.getPubDate() != null) {
            article_date_txt.setText(DateUtility.convertingDate(docs.getPubDate()));
        }

        article_title_txt.setText(docs.getTypeOfMaterial());

        if(docs.getMultimedia().size()>0 && docs.getMultimedia().get(2) != null && docs.getMultimedia().size() > 0) {
            glide.load("https://www.nytimes.com/"+docs.getMultimedia().get(2).getUrl()).into(article_picture_img);
        } else article_picture_img.setImageResource(R.drawable.empty_picture);
    }
}
