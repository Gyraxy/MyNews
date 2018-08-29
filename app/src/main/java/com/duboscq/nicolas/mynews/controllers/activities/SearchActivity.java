package com.duboscq.nicolas.mynews.controllers.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.adapters.DocsRecyclerViewAdapter;
import com.duboscq.nicolas.mynews.models.Docs;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
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
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchActivity extends AppCompatActivity{

    //--FOR DESIGN--

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

    //--FOR DATA--
    String search_query,begin_date,end_date,section;
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
        notification_switch.setVisibility(View.GONE);
        notification_view.setVisibility(View.GONE);
        configureSearchToolbar();
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

    @OnClick(R.id.activity_search_search_button)
    public void setSearch() {
        search_query = search_query_edt.getText().toString();
        if (search_query.length() == 0) {
            Toast.makeText(this, "Please search one term", Toast.LENGTH_SHORT).show();
        } else if (!arts_chb.isChecked()
                && !business_chb.isChecked()
                && !entrepreneurs_chb.isChecked()
                && !politics_chb.isChecked()
                && !sports_chb.isChecked()
                && !travel_chb.isChecked()) {
            Toast.makeText(this, "Please select at least one category", Toast.LENGTH_SHORT).show();
        } else if (!checkBeginDateBeforeEndDate()) {
            Toast.makeText(this, "Begin Date is after End Date, please modify", Toast.LENGTH_SHORT).show();
        } else {
            checkBoxName();
            getSearchParameters();
            configureAndShowArticleHTTP();
            savedSearchParameters();
        }
    }

    // -----------------------------
    // SEARCHACTIVITY DESIGN TOOLBAR
    // -----------------------------

    private void configureSearchToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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

    // ---------------------------------------------
    // DATE PICKER DIALOG CONFIGURATION
    // ---------------------------------------------

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

    // ---------------------------------------------------------
    // API CALL AND SHOW RECYCLERVIEW IF RESULTS OR POPUP IF NOT
    // ---------------------------------------------------------

    private void configureAndShowArticleHTTP(){
            disposable = APIStreams.getSearchDocs("\""+search_query+"\""+" AND section_name.contains:(\""+section+"\")",begin_date,end_date).subscribeWith(new DisposableObserver<GeneralInfo>() {
                @Override
                public void onNext(GeneralInfo generalInfo) {
                    Log.e("TAG", "SearchActivity : On Next");
                    updateArticles(generalInfo);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("TAG", "SearchActivity : On Error" + Log.getStackTraceString(e));
                }

                @Override
                public void onComplete() {
                    Log.e("TAG", "SearchActivity : On Complete !!");
                }
            });
        }

    //POPUP WHEN NO RESULTS WHERE FOUND

    private void noResultsPopup (){
        final AlertDialog.Builder no_results_popup = new AlertDialog.Builder(this);
        no_results_popup.setTitle("Informations");
        no_results_popup.setMessage("No results were found. Please try other words");
        no_results_popup.show();
    }

    private boolean checkBeginDateBeforeEndDate(){
        int begin_date_edt = Integer.parseInt(DateUtility.convertingSearchDate(search_begin_date_edt.getText().toString()));
        int end_date_edt = Integer.parseInt(DateUtility.convertingSearchDate(search_end_date_edt.getText().toString()));
        if (begin_date_edt < end_date_edt){
            return true;
        }
        return false;
    }

    private void updateArticles(GeneralInfo generalInfo) {
        docs = generalInfo.getResponse().getDocs();
        if (docs.isEmpty()) {
            noResultsPopup();
        } else {
            Log.e("TAG","Begin Date "+begin_date+"End Date "+end_date+"Query :"+search_query);
            configureRecyclerView();
            configureOnClickRecyclerView();
        }
    }

    private void checkBoxName() {
        if (arts_chb.isChecked()) {
            section = "arts";
        }
        if (business_chb.isChecked()) {
            section = "business";
        }
        if (entrepreneurs_chb.isChecked()) {
            section = "entrepreneurs";
        }
        if (politics_chb.isChecked()) {
            section = "politics";
        }
        if (sports_chb.isChecked()) {
            section = "sports";
        }
        if (travel_chb.isChecked()) {
            section = "travel";
        }
    }

    private void getSearchParameters(){
        begin_date = DateUtility.convertingSearchDate(search_begin_date_edt.getText().toString());
        end_date = DateUtility.convertingSearchDate(search_end_date_edt.getText().toString());
        search_query = search_query_edt.getText().toString();
    }

    private void savedSearchParameters(){
        SharedPreferencesUtility.putString(this,"SEARCH_SECTION",section);
        SharedPreferencesUtility.putString(this,"SEARCH_BEGIN_DATE",begin_date);
        SharedPreferencesUtility.putString(this,"SEARCH_END_DATE",end_date);
        SharedPreferencesUtility.putString(this,"SEARCH_QUERY",search_query);
    }
}
