package com.duboscq.nicolas.mynews.controllers.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.models.Docs;
import com.duboscq.nicolas.mynews.models.GeneralInfo;
import com.duboscq.nicolas.mynews.utils.APIStreams;
import com.duboscq.nicolas.mynews.utils.SharedPreferencesUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Nicolas DUBOSCQ on 05/09/2018
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static final int ID_NOTIFICATION = 1984;
    String CHANNEL_ID = "my_channel_01",todayDateformat,notification_query,section_notification;
    int arts_chb_st,business_chb_st,entrepreneurs_chb_st,politics_chb_st,sports_chb_st,travel_chb_st,switch_st;
    CharSequence name = "my_channel";
    Disposable disposable;
    List<Docs> docs;

    @Override
    public void onReceive(Context context, Intent intent) {
        notification_query = SharedPreferencesUtility.getInstance(context).getString("NOTIFICATION_QUERY",null);
        Log.e("TAG","Notification Query"+notification_query);
        getNotificationSection(context);
        Log.e("TAG","Notification Section"+section_notification);
        getTodayDate();
        getAPIDocs(context);
    }

    private void addNotification(Context context){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription("Test");
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setContentTitle("Notifications")
                .setContentText("We have found "+docs.size()+" new articles")
                .setSmallIcon(R.drawable.ic_news);

        notificationManager.notify(ID_NOTIFICATION, builder.build());
    }

    private void getAPIDocs(final Context context){

        disposable = APIStreams.getSearchDocs("\""+notification_query+"\""+" AND section_name.contains:("+section_notification+")",todayDateformat,todayDateformat).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.e("TAG", "Notification : Stream on Next");
                docs = generalInfo.getResponse().getDocs();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "Notification : Stream On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "Notification : Stream On Complete");
                addNotification(context);
            }
        });
    }

    private void getNotificationSection (Context context){
        String section_arts = "",
                section_business = "",
                section_entrepreneurs = "",
                section_politics = "",
                section_sports = "",
                section_travel = "";
        arts_chb_st = SharedPreferencesUtility.getInt(context,"ARTS_CHB",-1);
        business_chb_st = SharedPreferencesUtility.getInt(context,"BUSINESS_CHB",-1);
        entrepreneurs_chb_st = SharedPreferencesUtility.getInt(context,"ENTREPRENEURS_CHB",-1);
        politics_chb_st = SharedPreferencesUtility.getInt(context,"POLITICS_CHB",-1);
        sports_chb_st = SharedPreferencesUtility.getInt(context,"SPORTS_CHB",-1);
        travel_chb_st = SharedPreferencesUtility.getInt(context,"TRAVEL_CHB",-1);
        switch_st = SharedPreferencesUtility.getInt(context,"NOTIFICATION_SWITCH",-1);

        if (arts_chb_st == 1){
            section_arts = "arts";
        }
        if (business_chb_st ==1 ){
            section_business = "business";
        }
        if (entrepreneurs_chb_st == 1){
            section_entrepreneurs = "entrepreneurs";
        }
        if (politics_chb_st == 1){
            section_politics = "politics";
        }
        if (travel_chb_st == 1){
            section_travel = "travel";
        }
        section_notification = "\""+section_arts+"\""+"\""+section_business+"\""+"\""+section_entrepreneurs+"\""+"\""+section_politics+"\""+"\""+section_sports+"\""+"\""+section_travel+"\"";
    }

    private void getTodayDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);
        Date date = new Date();
        todayDateformat = (dateFormat.format(date));
        Log.e("TAG",todayDateformat);
    }
}
