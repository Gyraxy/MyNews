package com.duboscq.nicolas.mynews.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Locale;

/**
 * Created by Nicolas DUBOSCQ on 10/08/2018
 */
public class ArticleWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        String article_url=this.getIntent().getExtras().getString("article_url");
        webview.loadUrl(article_url);
        setContentView(webview);
    }
}