package com.example.android.usnationalparkguide.Utils.NetworkSync.NPS;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NPSApiConnection {

    public static NPSApiEndpointInterface getApi () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NPSApiEndpointInterface.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(NPSApiEndpointInterface.class);
    }

}
