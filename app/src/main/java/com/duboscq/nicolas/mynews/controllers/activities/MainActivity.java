package com.duboscq.nicolas.mynews.controllers.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR DESIGN
    @BindView(R.id.activity_main_tabs) TabLayout tabs;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.configureMainToolbar();
        this.configureViewPagerAndTabs();
        this.configureDrawerLayout();
        this.configureNavigationView();
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
        tabs= findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    //NAVIGATION DRAWER

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activity_main_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Configure Navigation Drawer OnClick
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        tabs.getTabAt(2).select();
        switch (item.getItemId()){
            case R.id.activity_main_drawer_arts:
                tabs.getTabAt(2).setText("ARTS");
                break;
            case R.id.activity_main_drawer_business:
                tabs.getTabAt(2).setText("BUSINESS");
                break;
            case R.id.activity_main_drawer_entrepreneurs:
                tabs.getTabAt(2).setText("ENTREPRENEURS");
                break;
            case R.id.activity_main_drawer_politics:
                tabs.getTabAt(2).setText("POLITICS");
                break;
            case R.id.activity_main_drawer_sports:
                tabs.getTabAt(2).setText("SPORTS");
                break;
            case R.id.activity_main_drawer_travel:
                tabs.getTabAt(2).setText("TRAVEL");
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
