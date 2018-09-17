package com.duboscq.nicolas.mynews.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.duboscq.nicolas.mynews.R;

/**
 * Created by Nicolas DUBOSCQ on 10/08/2018
 */
public class ArticleWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web_view);
        configureSearchToolbar();
        WebView webview = findViewById(R.id.activity_article_wbview);
        String article_url=this.getIntent().getExtras().getString("article_url");
        webview.loadUrl(article_url);
    }

    private void configureSearchToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}