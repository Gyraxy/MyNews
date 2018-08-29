package com.duboscq.nicolas.mynews.utils;

import com.duboscq.nicolas.mynews.models.GeneralInfo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nicolas DUBOSCQ on 27/08/2018
 */
public class APIStreams {

    public static Observable<GeneralInfo> getTopstoriesArticles(){
        APIInterface apiInterface = RetrofitUtility.getInstance().create(APIInterface.class);
        return apiInterface.getTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<GeneralInfo> getMostPopularArticles(){
        APIInterface apiInterface = RetrofitUtility.getInstance().create(APIInterface.class);
        return apiInterface.getMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<GeneralInfo> getWeeklyArticles(String section_name){
        APIInterface apiInterface = RetrofitUtility.getInstance().create(APIInterface.class);
        return apiInterface.getWeekly(section_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<GeneralInfo> getSearchDocs(String query,String begin_date,String end_date){
        APIInterface apiInterface = RetrofitUtility.getInstance().create(APIInterface.class);
        return apiInterface.getSearch(query,begin_date,end_date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}