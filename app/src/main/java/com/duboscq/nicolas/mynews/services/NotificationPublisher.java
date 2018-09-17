package com.duboscq.nicolas.mynews.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.duboscq.nicolas.mynews.R;
import com.duboscq.nicolas.mynews.controllers.activities.NotificationResultsActivity;
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
    String CHANNEL_ID = "my_channel_01",todayDateformat,todayDateformatplus,notification_query,notification_section;
    CharSequence name = "my_channel";
    Disposable disposable;
    List<Docs> docs;

    @Override
    public void onReceive(Context context, Intent intent) {
        notification_query = SharedPreferencesUtility.getInstance(context).getString("NOTIFICATION_QUERY",null);
        notification_section = SharedPreferencesUtility.getInstance(context).getString("NOTIFICATION_SECTION",null);
        Log.i("NETWORK","Notification Query"+notification_query);
        Log.i("NETWORK","Notification Section"+notification_section);
        getTodayDate();
        getAPIDocs(context);
    }

    private void addNotification(Context context){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, NotificationResultsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription("Test");
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setContentTitle("Notifications")
                .setContentText("We have found "+docs.size()+" new articles")
                .setSmallIcon(R.drawable.ic_news)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(ID_NOTIFICATION, builder.build());
    }

    private void getAPIDocs(final Context context){

        disposable = APIStreams.getSearchDocs("\""+notification_query+"\""+" AND section_name.contains:("+notification_section+")",todayDateformat,todayDateformatplus).subscribeWith(new DisposableObserver<GeneralInfo>() {
            @Override
            public void onNext(GeneralInfo generalInfo) {
                Log.i("NETWORK", "Notification : Stream on Next");
                docs = generalInfo.getResponse().getDocs();
            }

            @Override
            public void onError(Throwable e) {
                Log.i("NETWORK", "Notification : Stream On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.i("NETWORK", "Notification : Stream On Complete");
                addNotification(context);
            }
        });
    }

    private void getTodayDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.FRANCE);
        Date date = new Date();
        todayDateformat = (dateFormat.format(date));
        todayDateformatplus = String.valueOf(Integer.parseInt(todayDateformat)+1);
    }
}
