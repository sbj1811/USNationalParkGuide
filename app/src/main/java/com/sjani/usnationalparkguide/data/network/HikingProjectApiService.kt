package com.sjani.usnationalparkguide.data.network

import com.sjani.usnationalparkguide.data.model.TrailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HikingProjectApiService {
    
    companion object {
        const val BASE_URL = "https://www.hikingproject.com/"
    }
    
    @GET("data/get-trails")
    suspend fun getTrails(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 10,
        @Query("maxDistance") maxDistance: Int = 30
    ): TrailResponse
}
