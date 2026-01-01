package com.sjani.usnationalparkguide.data.network

import com.sjani.usnationalparkguide.data.model.AlertResponse
import com.sjani.usnationalparkguide.data.model.CampgroundResponse
import com.sjani.usnationalparkguide.data.model.ParksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NPSApiService {
    
    companion object {
        const val BASE_URL = "https://developer.nps.gov/"
    }
    
    @GET("api/v1/parks")
    suspend fun getParks(
        @Query("stateCode") stateCode: String,
        @Query("api_key") apiKey: String,
        @Query("fields") fields: String,
        @Query("limit") limit: String
    ): ParksResponse
    
    @GET("api/v1/campgrounds")
    suspend fun getCampgrounds(
        @Query("parkCode") parkCode: String,
        @Query("api_key") apiKey: String,
        @Query("fields") fields: String
    ): CampgroundResponse
    
    @GET("api/v1/alerts")
    suspend fun getAlerts(
        @Query("parkCode") parkCode: String,
        @Query("api_key") apiKey: String
    ): AlertResponse
}
