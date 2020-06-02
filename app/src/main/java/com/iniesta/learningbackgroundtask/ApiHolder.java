package com.iniesta.learningbackgroundtask;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHolder {


    @GET("tweets.json")
    Call<String> getTweets(@Query("q") String hastag, @Query("result_type") String recent, @Query("geocode") String geocode);
}
