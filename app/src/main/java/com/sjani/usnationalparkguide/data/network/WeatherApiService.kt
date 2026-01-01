package com.sjani.usnationalparkguide.data.network

import com.sjani.usnationalparkguide.data.model.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }
    
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "imperial"
    ): CurrentWeather
}
