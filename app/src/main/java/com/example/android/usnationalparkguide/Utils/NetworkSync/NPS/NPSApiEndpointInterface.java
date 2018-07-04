package com.example.android.usnationalparkguide.Utils.NetworkSync.NPS;

import com.example.android.usnationalparkguide.Models.Park.Parks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NPSApiEndpointInterface {

    String API_ENDPOINT = "https://developer.nps.gov";

    @GET("/api/v1/parks")
    Call<Parks> getParks(@Query("stateCode") String state, @Query("api_key") String apiKey, @Query("fields") String fields);


}
