package com.duboscq.nicolas.mynews.controllers.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    //FOR DESIGN
    private Toolbar toolbar;

    //FOR DATA


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureMainToolbar();
        this.configureViewPagerAndTabs();
    }


    //MENU DISPLAY
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    //MENU ACTIONS
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_notifications:
                Intent notification_intent = new Intent(this ,NotificationsActivity.class);
                startActivity(notification_intent);
                break;
            case R.id.action_help:
                break;
            case R.id.action_about:
                break;
            case R.id.action_search:
                Intent search_intent = new Intent(this ,SearchActivity.class);
                startActivity(search_intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //TOOLBAR CONFIGURATION
    private void configureMainToolbar(){
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //VIEWPAGER CONFIGURATION AND TABS
    private void configureViewPagerAndTabs(){

        ViewPager pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        TabLayout tabs= findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
