package com.sjani.usnationalparkguide.Utils.NetworkSync.Trails;

import com.sjani.usnationalparkguide.Models.Park.Parks;
import com.sjani.usnationalparkguide.Models.Trails.Trail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HikingprojectEndpointInterface {

    String API_ENDPOINT = "https://www.hikingproject.com";

    @GET("/data/get-trails")
    Call<Trail> getTrails(@Query("lat") String lat, @Query("lon") String lon , @Query("key") String apiKey);

}
