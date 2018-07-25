package com.sjani.usnationalparkguide.Utils.NetworkSync.NPS;

import com.sjani.usnationalparkguide.Models.Alerts.Alert;
import com.sjani.usnationalparkguide.Models.Campgrounds.Campground;
import com.sjani.usnationalparkguide.Models.Park.Parks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NPSApiEndpointInterface {

    String API_ENDPOINT = "https://developer.nps.gov";

    @GET("/api/v1/parks")
    Call<Parks> getParks(@Query("stateCode") String state, @Query("api_key") String apiKey, @Query("fields") String fields, @Query("limit") String maxResults);

    @GET("/api/v1/campgrounds")
    Call<Campground> getCampgound(@Query("parkCode") String parkCode, @Query("api_key") String apiKey, @Query("fields") String fields_cg);

    @GET("/api/v1/alerts")
    Call<Alert> getAlerts(@Query("parkCode") String parkCode, @Query("api_key") String apiKey);

}
