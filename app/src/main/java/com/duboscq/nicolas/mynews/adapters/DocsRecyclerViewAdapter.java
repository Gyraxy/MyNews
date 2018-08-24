package com.duboscq.nicolas.mynews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.models.Docs;
import com.duboscq.nicolas.mynews.views.DocsViewHolder;

import java.util.List;

/**
 * Created by Nicolas DUBOSCQ on 20/08/2018
 */
public class DocsRecyclerViewAdapter extends RecyclerView.Adapter<DocsViewHolder>{

    private List<Docs> docs;
    private RequestManager glide;

    public DocsRecyclerViewAdapter(Context mContext,List<Docs> docs,RequestManager glide) {
        this.docs = docs;
        Context context = mContext;
        this.glide=glide;
    }

    @Override
    public DocsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.article_view_summary, parent, false);
        return new DocsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DocsViewHolder DocsViewHolder,int position) {
        DocsViewHolder.updateDocsinfo(this.docs.get(position), this.glide);
    }

    @Override
    public int getItemCount() {
        return docs.size();
    }

    public Docs getInfo(int position){
        return this.docs.get(position);
    }
}
