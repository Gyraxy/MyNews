package com.duboscq.nicolas.mynews.utils;

import com.duboscq.nicolas.mynews.models.GeneralInfo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nicolas DUBOSCQ on 08/08/2018
 */
public interface APIInterface {

        @GET("topstories/v2/home.json?api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Observable<GeneralInfo> getTopStories();

        @GET("mostpopular/v2/mostshared/all-sections/7.json?api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Observable<GeneralInfo> getMostPopular();

        @GET("search/v2/articlesearch.json?&facet_field=day_of_week&sort=newest&api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Observable<GeneralInfo> getWeekly(@Query("fq") String section_name);

        @GET("search/v2/articlesearch.json?&api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Observable<GeneralInfo> getSearch(@Query("fq")String search_query,
                                          @Query("begin_date") String begin_date,
                                          @Query("end_date") String end_date);

        @GET("search/v2/articlesearch.json?&api-key=c5ee5b8a2b004651bd6337f0f785469b")
        Observable<GeneralInfo> getSearchWithoutDate(@Query("fq")String search_query);

}
