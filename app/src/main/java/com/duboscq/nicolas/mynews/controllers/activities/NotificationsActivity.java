package com.duboscq.nicolas.mynews.controllers.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.duboscq.nicolas.mynews.R;

/**
 * Created by Nicolas DUBOSCQ on 25/07/2018
 */
public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notifications);
        setTitle("Notifications");
        LinearLayout linearLayout = findViewById(R.id.activity_search_date_layout);
        Button search_button = findViewById(R.id.activity_search_search_button);
        search_button.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        configureSearchToolbar();
    }

    private void configureSearchToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
