package com.sjani.usnationalparkguide.Utils.NetworkSync.Weather;

import com.sjani.usnationalparkguide.Models.Weather.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApiEndpointInterface {

    String API_ENDPOINT = "https://api.openweathermap.org";

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String apiKey, @Query("units") String units);

}
