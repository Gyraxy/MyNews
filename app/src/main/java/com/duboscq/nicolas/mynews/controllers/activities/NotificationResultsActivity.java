package com.duboscq.nicolas.mynews.controllers.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.DocsRecyclerViewAdapter;
import com.duboscq.nicolas.mynews.models.Docs;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.utils.APIStreams;
import com.duboscq.nicolas.mynews.utils.ItemClickSupport;
import com.duboscq.nicolas.mynews.utils.SharedPreferencesUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationResultsActivity extends AppCompatActivity {

    @BindView(R.id.notification_recycler_view)
    RecyclerView recyclerView;

    //FOR DATA
    private Toolbar toolbar;
    private Disposable disposable;
    List<Docs> docs;
    DocsRecyclerViewAdapter adapter;
    String todayDateformat,todayDateformatplus,notification_query,notification_section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_results);
        ButterKnife.bind(this);
        this.configureMainToolbar();
        getTodayDate();
        notification_section = SharedPreferencesUtility.getString(this,"NOTIFICATION_SECTION");
        notification_query = SharedPreferencesUtility.getString(this,"NOTIFICATION_QUERY");
        configureRecyclerView();
        configureAndShowArticleHTTP();
        configureOnClickRecyclerView();
    }

    private void configureMainToolbar(){
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_articles)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent i = new Intent(getApplicationContext(),ArticleWebViewActivity.class);
                        i.putExtra("article_url",docs.get(position).getWebUrl());
                        startActivity(i);
                    }
                });
    }

    private void configureRecyclerView (){
        this.docs = new ArrayList<>();
        this.adapter = new DocsRecyclerViewAdapter(this.docs, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void getTodayDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);
        Date date = new Date();
        todayDateformat = (dateFormat.format(date));
        todayDateformatplus = String.valueOf(Integer.parseInt(todayDateformat)+1);
        Log.e("TAG",todayDateformat);
    }

    private void configureAndShowArticleHTTP() {
        disposable = APIStreams.getSearchDocs("\""+notification_query+"\""+" AND section_name.contains:("+notification_section+")",todayDateformat,todayDateformatplus).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i("NETWORK", "NotificationResults : On Next");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("NETWORK", "NotificationResults : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i("NETWORK", "NotificationResults : On Complete !!");
            }
        });
    }

    private void updateArticles(GeneralInfo generalInfo){
        docs.addAll(generalInfo.getResponse().getDocs());
        adapter.notifyDataSetChanged();
    }
}
