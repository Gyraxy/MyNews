package com.duboscq.nicolas.mynews.controllers.activities;

/**
 * Created by Nicolas DUBOSCQ on 30/08/2018
 */

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.DocsRecyclerViewAdapter;
import com.duboscq.nicolas.mynews.models.Docs;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.services.NotificationPublisher;
import com.duboscq.nicolas.mynews.utils.APIStreams;
import com.duboscq.nicolas.mynews.utils.DateUtility;
import com.duboscq.nicolas.mynews.utils.ItemClickSupport;
import com.duboscq.nicolas.mynews.utils.SharedPreferencesUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchNotificationActivity extends AppCompatActivity{

    //--FOR DESIGN--

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.activity_notification_switch) Switch notification_switch;
    @BindView(R.id.activity_notification_view) View notification_view;

    //Checkbox
    @BindView(R.id.activity_search_chb_arts) CheckBox arts_chb;
    @BindView(R.id.activity_search_chb_business) CheckBox business_chb;
    @BindView(R.id.activity_search_chb_entrepreneurs) CheckBox entrepreneurs_chb;
    @BindView(R.id.activity_search_chb_politics) CheckBox politics_chb;
    @BindView(R.id.activity_search_chb_sports) CheckBox sports_chb;
    @BindView(R.id.activity_search_chb_travel) CheckBox travel_chb;

    //Search Button
    @BindView(R.id.activity_search_search_button) Button search_button;

    //Edit Text
    @BindView(R.id.activity_search_search_edt) EditText search_query_edt;
    @BindView(R.id.activity_search_begin_date_edt) EditText search_begin_date_edt;
    @BindView(R.id.activity_search_end_date_edt) EditText search_end_date_edt;

    //Layout
    @BindView(R.id.activity_search_date_layout) LinearLayout search_date_layout;
    @BindView(R.id.activity_search_button_layout) LinearLayout search_button_layout;
    @BindView(R.id.activity_notification_switch_layout) LinearLayout switch_layout;


    //--FOR DATA--
    String search_query,notification_query,begin_date=null,end_date,search_section=null,notification_section=null,section_chbx_arts,section_chbx_business,section_chbx_entrepreneurs,section_chbx_politics,section_chbx_sports,section_chbx_travel;
    String activity;
    Calendar newDate = Calendar.getInstance();
    DatePickerDialog mDatePickerDialogbegin,mDatePickerDialogend;
    List<Docs> docs;
    private Disposable disposable;
    DocsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notifications);
        ButterKnife.bind(this);
        activity = getIntent().getExtras().getString("NOTIFICATION_SEARCH_ACTIVITY");
        Log.i("DATA",activity);
        configureSearchToolbar();
        switch (activity) {
            case "search":
                setSearchActivityLayout();
                break;
            case "notification":
                setNotificationActivityLayout();
                getNotificationParameters();
                search_query_edt.setSelection(search_query_edt.getText().length());
                break;
            default:
                break;
        }
        setBeginDate();
        setEndDate();
        search_begin_date_edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialogbegin.show();
                return false;
            }
        });
        search_end_date_edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialogend.show();
                return false;
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        System.out.println("MainActivity:onStart()"+search_query_edt.length());
    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("MainActivity:onResume()"+search_query_edt.length());
    }

    @Override
    protected void onPause(){
        super.onPause();
        System.out.println("MainActivity:onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        System.out.println("MainActivity:onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        System.out.println("MainActivity:onDestroy()");
    }

    // -------------
    // LAYOUT DESIGN
    // -------------

    //FOR SEARCH ACTIVITY
    private void setSearchActivityLayout(){
        setTitle("Search");
        switch_layout.setVisibility(View.GONE);
        notification_view.setVisibility(View.GONE);
    }

    //FOR NOTIFICATION ACTIVITY
    private void setNotificationActivityLayout(){
        setTitle("Notifications");
        search_date_layout.setVisibility(View.GONE);
        search_button_layout.setVisibility(View.GONE);

    }

    // -------
    // ACTIONS
    // -------

    @OnClick(R.id.activity_search_search_button)
    public void setSearch() {
        getSearchParameters();
        if (search_query.length() == 0) {
            Toast.makeText(this, "Please search one term", Toast.LENGTH_SHORT).show();
        } else if (!arts_chb.isChecked()
                && !business_chb.isChecked()
                && !entrepreneurs_chb.isChecked()
                && !politics_chb.isChecked()
                && !sports_chb.isChecked()
                && !travel_chb.isChecked()) {
            Toast.makeText(this, "Please select at least one category", Toast.LENGTH_SHORT).show();
        } else if (begin_date==null && end_date==null){
            Log.e("TAG","Recherche sans date");
            configureSection();
            configureAndShowArticleHTTPWithoutDate();
            saveSearchParameters();
        } else if (begin_date!=null && end_date!=null){
            if (checkBeginDateBeforeEndDate()){
                Toast.makeText(this, "Begin date is after End Date, please modify", Toast.LENGTH_SHORT).show();
            } else {
                configureSection();
                configureAndShowArticleHTTP();
                saveSearchParameters();
            }
        } else if (begin_date==null && end_date!=null) {
            configureSection();
            configureAndShowArticleHTTPWithoutBeginDate();
            saveSearchParameters();
        } else if (begin_date!=null && end_date==null) {
            configureSection();
            configureAndShowArticleHTTPWithoutEndDate();
            saveSearchParameters();
        }
    }

    @OnCheckedChanged(R.id.activity_notification_switch)
            public void switchChanged(CompoundButton button,boolean checked){
                if (checked){
                    if (!arts_chb.isChecked()
                            && !business_chb.isChecked()
                            && !entrepreneurs_chb.isChecked()
                            && !politics_chb.isChecked()
                            && !sports_chb.isChecked()
                            && !travel_chb.isChecked()){
                        Toast.makeText(this, "Please select at least one category", Toast.LENGTH_SHORT).show();
                        notification_switch.setChecked(false);
                    } else if (search_query_edt.length() == 0){
                        Toast.makeText(this, "Please search for one term", Toast.LENGTH_SHORT).show();
                        notification_switch.setChecked(false);
                    }
                    else {
                        configureSection();
                        saveNotificationParameters();
                        Log.i("UI","Switch checked");
                        scheduleDailyNotification();
                        Toast.makeText(SearchNotificationActivity.this, "Notification scheduled", Toast.LENGTH_LONG).show();
                    }
        } else if (!checked){
            Log.i("UI","Switch unchecked");
            cancelDailyNotification();
            Toast.makeText(SearchNotificationActivity.this, "Notification stopped", Toast.LENGTH_LONG).show();
        }
    }

    // ---------------------
    // TOOLBAR CONFIGURATION
    // ---------------------

    private void configureSearchToolbar() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent notification_intent = new Intent(this ,MainActivity.class);
                switch (activity) {
                    case "search":
                        break;
                    case "notification":
                        configureSection();
                        saveNotificationParameters();
                        break;
                    default:
                        break;
                }
                startActivity(notification_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ---------------------------------------------
    // CONFIGURE RECYCLERVIEW + ONCLICK RECYCLERVIEW
    // ---------------------------------------------

    //RECYCLER VIEW
    private void configureRecyclerView(){
        setContentView(R.layout.fragment_articles);
        recyclerView = findViewById(R.id.article_recycler_view);
        swipeRefreshLayout = findViewById(R.id.article_swipe_container);
        swipeRefreshLayout.setEnabled(false);
        this.adapter = new DocsRecyclerViewAdapter(this.docs,Glide.with(this));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.recyclerView.setAdapter(this.adapter);
    }

    //ONCLICK RECYCLERVIEW
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

    // METHOD TO CHECK BEGINDATE IS AFTER ENDDATE

    private boolean checkBeginDateBeforeEndDate(){
        Date begin_date_edt_date = DateUtility.convertingSearchStringDate(search_begin_date_edt.getText().toString());
        Date end_date_edt_date = DateUtility.convertingSearchStringDate(search_end_date_edt.getText().toString());

        if (begin_date_edt_date.after(end_date_edt_date)){
            return true;
        }
        return false;
    }

    // ---------------------------------------------------------
    // API CALL AND SHOW RECYCLERVIEW IF RESULTS OR POPUP IF NOT
    // ---------------------------------------------------------

    public void configureAndShowArticleHTTP(){
        disposable = APIStreams.getSearchDocs("\""+search_query+"\""+" AND section_name.contains:("+search_section+")",begin_date,end_date).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i("NETWORK", "SearchActivity : On Next");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("NETWORK", "SearchActivity : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i("NETWORK", "SearchActivity : On Complete !!");
            }
        });
    }

    private void configureAndShowArticleHTTPWithoutDate(){
        disposable = APIStreams.getSearchDocsWithoutDate("\""+search_query+"\""+" AND section_name.contains:("+search_section+")").subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i("NETWORK", "SearchActivity : On Next HTTP Request without date");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("NETWORK", "SearchActivity : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i("NETWORK", "SearchActivity : On Complete HTTP Request without date !!");
            }
        });
    }

    private void configureAndShowArticleHTTPWithoutBeginDate(){
        disposable = APIStreams.getSearchDocsWithoutBeginDate("\""+search_query+"\""+" AND section_name.contains:("+search_section+")",end_date).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i("NETWORK", "SearchActivity : On Next HTTP Request without Begin date");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("NETWORK", "SearchActivity : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i("NETWORK", "SearchActivity : On Complete HTTP Request without Begin date");
            }
        });
    }

    private void configureAndShowArticleHTTPWithoutEndDate(){
        disposable = APIStreams.getSearchDocsWithoutEndDate("\""+search_query+"\""+" AND section_name.contains:("+search_section+")",begin_date).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i("NETWORK", "SearchActivity : On Next HTTP Request without End date");
                updateArticles(generalInfo);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("NETWORK", "SearchActivity : On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i("NETWORK", "SearchActivity : On Complete HTTP Request without End date");
            }
        });
    }

    //POPUP WHEN NO RESULTS WHERE FOUND

    private void noResultsPopup (){
        final AlertDialog.Builder no_results_popup = new AlertDialog.Builder(this);
        no_results_popup.setTitle("Informations");
        no_results_popup.setMessage("No results were found. Please try other words, select different section or change the period.");
        no_results_popup.show();
    }

    //UPDATE RECYCLERVIEW ADAPTER WITH ARTICLES FOUND

    private void updateArticles(GeneralInfo generalInfo) {
        docs = generalInfo.getResponse().getDocs();
        if (docs.isEmpty()) {
            noResultsPopup();
        } else {
            configureRecyclerView();
            configureOnClickRecyclerView();
        }
    }

    private void configureSection() {

        //Initialization of String
        section_chbx_arts=null;
        section_chbx_business=null;
        section_chbx_entrepreneurs=null;
        section_chbx_politics=null;
        section_chbx_sports=null;
        section_chbx_travel=null;

        //Check if checkboxes are "checked" and if this is the case
        if (arts_chb.isChecked()) {
            section_chbx_arts = "arts";
        }
        if (business_chb.isChecked()) {
            section_chbx_business = "business";
        }
        if (entrepreneurs_chb.isChecked()) {
            section_chbx_entrepreneurs = "entrepreneurs";
        }
        if (politics_chb.isChecked()) {
            section_chbx_politics = "politics";
        }
        if (sports_chb.isChecked()) {
            section_chbx_sports = "sports";
        }
        if (travel_chb.isChecked()) {
            section_chbx_travel = "travel";
        }

        //Configure the section name for search or notification activity depending of current activity

        switch (activity) {
            case "search":
                search_section = "\""+section_chbx_arts+"\""+"\""+section_chbx_business+"\""+"\""+section_chbx_entrepreneurs+"\""+"\""+section_chbx_politics+"\""+"\""+section_chbx_sports+"\""+"\""+section_chbx_travel+"\"";
                break;
            case "notification":
                notification_section = "\""+section_chbx_arts+"\""+"\""+section_chbx_business+"\""+"\""+section_chbx_entrepreneurs+"\""+"\""+section_chbx_politics+"\""+"\""+section_chbx_sports+"\""+"\""+section_chbx_travel+"\"";
                break;
            default:
                break;
        }
    }

    // ----------------------------------
    // DATA FROM EDIT TEXT AND CHECKBOXES
    // ----------------------------------

    private void getSearchParameters(){
        if (search_begin_date_edt.getText().length()==0){
            begin_date = null;
        } else if (search_begin_date_edt.getText().length()>0){
            begin_date = DateUtility.convertingSearchDate(search_begin_date_edt.getText().toString());
        }
        if (search_end_date_edt.getText().length()==0){
            end_date = null;
        } else if (search_end_date_edt.getText().length()>0){
            end_date = String.valueOf(Integer.parseInt(DateUtility.convertingSearchDate(search_end_date_edt.getText().toString()))+1);
        }
        search_query = search_query_edt.getText().toString();
    }

    private void saveSearchParameters(){
        SharedPreferencesUtility.putString(this,"SEARCH_SECTION",search_section);
        SharedPreferencesUtility.putString(this,"SEARCH_BEGIN_DATE",begin_date);
        SharedPreferencesUtility.putString(this,"SEARCH_END_DATE",end_date);
        SharedPreferencesUtility.putString(this,"SEARCH_QUERY",search_query);
    }

    private void getNotificationParameters(){

        int arts_chb_st = SharedPreferencesUtility.getInt(getApplicationContext(),"ARTS_CHB",-1);
        int business_chb_st = SharedPreferencesUtility.getInt(getApplicationContext(),"BUSINESS_CHB",-1);
        int entrepreneurs_chb_st = SharedPreferencesUtility.getInt(getApplicationContext(),"ENTREPRENEURS_CHB",-1);
        int politics_chb_st = SharedPreferencesUtility.getInt(getApplicationContext(),"POLITICS_CHB",-1);
        int sports_chb_st = SharedPreferencesUtility.getInt(getApplicationContext(),"SPORTS_CHB",-1);
        int travel_chb_st = SharedPreferencesUtility.getInt(getApplicationContext(),"TRAVEL_CHB",-1);
        int switch_st = SharedPreferencesUtility.getInt(getApplicationContext(),"NOTIFICATION_SWITCH",-1);
        notification_query =SharedPreferencesUtility.getString(getApplicationContext(),"NOTIFICATION_QUERY");

        if (arts_chb_st == 1){
            arts_chb.setChecked(true);
        } else if (arts_chb_st == 0 || arts_chb_st == -1){
            arts_chb.setChecked(false);
        }
        if (business_chb_st == 1){
            business_chb.setChecked(true);
        } else if (business_chb_st == 0 || business_chb_st == -1){
            business_chb.setChecked(false);
        }
        if (entrepreneurs_chb_st == 1){
            entrepreneurs_chb.setChecked(true);
        } else if (entrepreneurs_chb_st == 0 || entrepreneurs_chb_st == -1){
            entrepreneurs_chb.setChecked(false);
        }
        if (politics_chb_st == 1){
            politics_chb.setChecked(true);
        } else if (politics_chb_st == 0 || politics_chb_st == -1){
            politics_chb.setChecked(false);
        }
        if (sports_chb_st == 1){
            sports_chb.setChecked(true);
        } else if (sports_chb_st == 0 || sports_chb_st == -1){
            sports_chb.setChecked(false);
        }
        if (travel_chb_st == 1){
            travel_chb.setChecked(true);
        } else if (travel_chb_st == 0 || travel_chb_st == -1){
            travel_chb.setChecked(false);
        }
        if (switch_st == 1){
            notification_switch.setChecked(true);
        } else if (switch_st == 0 || switch_st == -1){
            notification_switch.setChecked(false);
        }
        search_query_edt.setText(notification_query);
    }

    private void saveNotificationParameters(){
        if (arts_chb.isChecked()){
            SharedPreferencesUtility.putInt(this,"ARTS_CHB",1);
        } else {SharedPreferencesUtility.putInt(this,"ARTS_CHB",0);}

        if (business_chb.isChecked()){
            SharedPreferencesUtility.putInt(this,"BUSINESS_CHB",1);
        } else {SharedPreferencesUtility.putInt(this,"BUSINESS_CHB",0);}

        if (entrepreneurs_chb.isChecked()){
            SharedPreferencesUtility.putInt(this,"ENTREPRENEURS_CHB",1);
        } else {SharedPreferencesUtility.putInt(this,"ENTREPRENEURS_CHB",0);}

        if (politics_chb.isChecked()){
            SharedPreferencesUtility.putInt(this,"POLITICS_CHB",1);
        } else {SharedPreferencesUtility.putInt(this,"POLITICS_CHB",0);}

        if (sports_chb.isChecked()){
            SharedPreferencesUtility.putInt(this,"SPORTS_CHB",1);
        } else {SharedPreferencesUtility.putInt(this,"SPORT_CHB",0);}

        if (travel_chb.isChecked()){
            SharedPreferencesUtility.putInt(this,"TRAVEL_CHB",1);
        } else {SharedPreferencesUtility.putInt(this,"TRAVEL_CHB",0);}

        if (notification_switch.isChecked()){
            SharedPreferencesUtility.putInt(this,"NOTIFICATION_SWITCH",1);
        } else {SharedPreferencesUtility.putInt(this,"NOTIFICATION_SWITCH",0);}

        SharedPreferencesUtility.putString(this,"NOTIFICATION_QUERY",search_query_edt.getText().toString());
        SharedPreferencesUtility.putString(this,"NOTIFICATION_SECTION",notification_section);
    }

    // --------------------------------
    // DATE PICKER DIALOG CONFIGURATION
    // --------------------------------

    private void setBeginDate() {

        mDatePickerDialogbegin = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                search_begin_date_edt.setText(fdate);

            }
        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialogbegin.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void setEndDate() {

        mDatePickerDialogend = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);
                search_end_date_edt.setText(fdate);

            }
        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialogend.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    // ----------------------------------
    // ALARM MANAGER TO SET NOTIFICATIONS
    // ----------------------------------

    //Notifications scheduled
    private void scheduleDailyNotification() {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar_notification = Calendar.getInstance();
        calendar_notification.set(Calendar.HOUR_OF_DAY, 7);
        calendar_notification.set(Calendar.MINUTE, 0);
        calendar_notification.set(Calendar.SECOND, 0);

        if (calendar_notification.getTimeInMillis() < System.currentTimeMillis()) {
            calendar_notification.add(Calendar.DATE, 1);
        }

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar_notification.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    //Notifications canceled
    private void cancelDailyNotification() {
        Intent intent = new Intent(this, NotificationPublisher.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.cancel(sender);
    }
}
