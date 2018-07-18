package com.example.android.usnationalparkguide.Utils.NetworkSync.Trails;

import com.example.android.usnationalparkguide.Utils.NetworkSync.NPS.NPSApiEndpointInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HikingprojectApiConnection {

    public static HikingprojectEndpointInterface getApi () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HikingprojectEndpointInterface.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(HikingprojectEndpointInterface.class);
    }

}
