package com.duboscq.nicolas.mynews.utils;

import com.duboscq.nicolas.mynews.models.GeneralInfo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Nicolas DUBOSCQ on 08/08/2018
 */
public interface APIInterface {

        @GET("topstories/v2/home.json?api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Call<GeneralInfo> getTopStories();

        @GET("mostpopular/v2/mostshared/all-sections/7.json?api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Call<GeneralInfo> getMostPopular();

        @GET("search/v2/articlesearch.json?q=new+york+times&page=2&sort=oldest&api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Call<GeneralInfo> getCustom();

}
