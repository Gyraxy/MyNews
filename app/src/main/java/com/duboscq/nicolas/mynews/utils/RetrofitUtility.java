package com.duboscq.nicolas.mynews.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nicolas DUBOSCQ on 10/08/2018
 */
public class RetrofitUtility {

    private static Retrofit INSTANCE = null;

    public static Retrofit getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Retrofit.Builder()
                    .baseUrl("http://api.nytimes.com/svc/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INSTANCE;
    }
}
