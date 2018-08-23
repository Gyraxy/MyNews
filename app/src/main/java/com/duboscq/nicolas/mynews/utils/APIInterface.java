package com.duboscq.nicolas.mynews.utils;

import com.duboscq.nicolas.mynews.models.GeneralInfo;

import retrofit2.Call;
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

        @GET("search/v2/articlesearch.json?&facet_field=day_of_week&sort=newest&api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Call<GeneralInfo> getWeekly(@Query("fq") String section_name);

        @GET("search/v2/articlesearch.json?&sort=newest&api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Call<GeneralInfo> getSearch(@Query("fq") String section_name,
                                    @Query("begin_date") String begin_date,
                                    @Query("end_date") String end_date);

}
