package com.duboscq.nicolas.mynews.utils;

import com.duboscq.nicolas.mynews.models.GeneralInfo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nicolas DUBOSCQ on 08/08/2018
 */
public interface APIInterface {

        @GET("topstories/v2/home.json?api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Call<GeneralInfo> getTopStories();

        @GET("mostpopular/v2/mostshared/all-sections/7.json?api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Call<GeneralInfo> getMostPopular();

        @GET("search/v2/articlesearch.json?&api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Call<GeneralInfo> getCustom(@Query("q") String section);

}
