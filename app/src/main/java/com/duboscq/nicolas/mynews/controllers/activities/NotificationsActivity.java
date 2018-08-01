package com.duboscq.nicolas.mynews.controllers.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.duboscq.nicolas.mynews.R;
import butterknife.ButterKnife;

/**
 * Created by Nicolas DUBOSCQ on 25/07/2018
 */
public class NotificationsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notifications);
        setTitle("Notifications");
        ButterKnife.bind(this);
        LinearLayout search_date_layout = findViewById(R.id.activity_search_date_layout);
        LinearLayout search_button_layout = findViewById(R.id.activity_search_button_layout);
        search_date_layout.setVisibility(View.GONE);
        search_button_layout.setVisibility(View.GONE);
        configureSearchToolbar();
    }

    private void configureSearchToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
