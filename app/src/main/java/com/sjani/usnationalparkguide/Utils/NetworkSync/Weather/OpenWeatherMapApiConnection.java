package com.sjani.usnationalparkguide.Utils.NetworkSync.Weather;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherMapApiConnection {

    public static OpenWeatherMapApiEndpointInterface getApi () {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherMapApiEndpointInterface.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(OpenWeatherMapApiEndpointInterface.class);
    }

}
