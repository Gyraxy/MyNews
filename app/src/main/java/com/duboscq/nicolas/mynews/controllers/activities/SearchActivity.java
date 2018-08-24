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
import com.duboscq.nicolas.mynews.utils.APIInterface;
import com.duboscq.nicolas.mynews.utils.DateUtility;
import com.duboscq.nicolas.mynews.utils.ItemClickSupport;
import com.duboscq.nicolas.mynews.utils.RetrofitUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.activity_search_search_edt) EditText search_edt;
    @BindView(R.id.activity_search_begin_date_edt) EditText search_begin_date;
    @BindView(R.id.activity_search_end_date_edt) EditText search_end_date;



    //--FOR DATA--
    String search_query;
    String begin_date;
    String end_date;
    String section;
    Calendar newDate = Calendar.getInstance();
    DatePickerDialog mDatePickerDialogbegin;
    DatePickerDialog mDatePickerDialogend;
    List<Docs> docs;
    private RecyclerView recyclerView;


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
        search_begin_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialogbegin.show();
                return false;
            }
        });
        search_end_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialogend.show();
                return false;
            }
        });
    }

    @OnClick(R.id.activity_search_search_button)
    public void setSearch() {
        search_query = search_edt.getText().toString();
        if (search_query.length() == 0) {
            Toast.makeText(this, "Please search one term", Toast.LENGTH_SHORT).show();
        } else if (arts_chb.isChecked() == false && business_chb.isChecked() == false && entrepreneurs_chb.isChecked() == false && politics_chb.isChecked() == false && sports_chb.isChecked() == false && travel_chb.isChecked() == false) {
            Toast.makeText(this, "Please select at least one category", Toast.LENGTH_SHORT).show();
        } else {
            getSearchInfo();
            checkBoxName();
            startSearchApiCall();
            //Intent new_search = new Intent(this, MainActivity.class);
            //new_search.putExtra("TABS", 2);
            //new_search.putExtra("BEGIN_DATE", begin_date);
            //new_search.putExtra("END_DATE", end_date);
            //new_search.putExtra("SECTION", section);
            //System.out.println(section);
            //startActivity(new_search);
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
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.article_swipe_container);
        recyclerView=findViewById(R.id.article_recycler_view);
        DocsRecyclerViewAdapter adapter = new DocsRecyclerViewAdapter(SearchActivity.this, docs, Glide.with(SearchActivity.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setEnabled(false);
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

    private void getSearchInfo() {
        begin_date = DateUtility.convertingSearchDate(search_begin_date.getText().toString());
        end_date = DateUtility.convertingSearchDate(search_end_date.getText().toString());
        search_query = search_edt.getText().toString();

    }

    private void checkBoxName() {
        if (arts_chb.isChecked() == true) {
            section = "arts";
        }
        if (business_chb.isChecked() == true) {
            section = "business";
        }
        if (entrepreneurs_chb.isChecked() == true) {
            section = "entrepreneurs";
        }
        if (politics_chb.isChecked() == true) {
            section = "politics";
        }
        if (sports_chb.isChecked() == true) {
            section = "sports";
        }
        if (travel_chb.isChecked() == true) {
            section = "travel";
        }
    }

    private void setBeginDate() {

        mDatePickerDialogbegin = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                search_begin_date.setText(fdate);

            }
        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialogbegin.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void setEndDate() {

        mDatePickerDialogend = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);
                search_end_date.setText(fdate);

            }
        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialogend.getDatePicker().setMaxDate(System.currentTimeMillis());

    }


    // ---------------------------------------------------------
    // API CALL AND SHOW RECYCLERVIEW IF RESULTS OR POPUP IF NOT
    // ---------------------------------------------------------

    private void startSearchApiCall() {

        APIInterface apiInterface = RetrofitUtility.getInstance().create(APIInterface.class);
        Call<GeneralInfo> call = apiInterface.getSearch(search_query,section,begin_date,end_date);
        call.enqueue(new Callback<GeneralInfo>() {
            @Override
            public void onResponse(Call<GeneralInfo> call, Response<GeneralInfo> response) {
                docs = response.body().getResponse().getDocs();
                if (docs.size()==0){
                    noResultsPopup();
                } else
                    configureRecyclerView();
                    configureOnClickRecyclerView();
            }

            @Override
            public void onFailure(Call<GeneralInfo> call, Throwable t) {
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
}
