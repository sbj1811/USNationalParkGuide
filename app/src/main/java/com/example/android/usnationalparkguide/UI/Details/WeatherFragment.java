package com.example.android.usnationalparkguide.UI.Details;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.usnationalparkguide.Models.Park.Parks;
import com.example.android.usnationalparkguide.Models.Weather.CurrentWeather;
import com.example.android.usnationalparkguide.Models.Weather.Main;
import com.example.android.usnationalparkguide.Models.Weather.Sys;
import com.example.android.usnationalparkguide.Models.Weather.Weather;
import com.example.android.usnationalparkguide.Models.Weather.Wind;
import com.example.android.usnationalparkguide.R;
import com.example.android.usnationalparkguide.Utils.NetworkSync.Weather.OpenWeatherMapApiConnection;
import com.example.android.usnationalparkguide.Utils.StringToGPSCordinates;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherFragment extends Fragment {

    private static final String TAG = WeatherFragment.class.getSimpleName();
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";


    @BindView(R.id.current_temp)
    TextView currentTempTextview;
    @BindView(R.id.weather_condition_text)
    TextView weatherConditionTextview;
    @BindView(R.id.sunrise_time)
    TextView sunriseTimeTextview;
    @BindView(R.id.sunset_time)
    TextView sunsetTimeTextview;
    @BindView(R.id.humidity_percent)
    TextView humidityTextview;
    @BindView(R.id.wind_speed)
    TextView windTextview;
    @BindView(R.id.min_temp_value)
    TextView minTempTextview;
    @BindView(R.id.max_temp_value)
    TextView maxTempTextview;

    private Uri uri;
    private String parkId;
    private int position;
    private String latLong;
    private List<Weather> currentWeather;
    private Main main;
    private Wind wind;
    private Sys sys;


    public WeatherFragment() {
        // Required empty public constructor
    }


    public static WeatherFragment newInstance(Uri uri, String parkId, int position, String latlong) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI, uri);
        args.putString(PARK_ID, parkId);
        args.putInt(POSITION,position);
        args.putString(LATLONG,latlong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_weather, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            uri = getArguments().getParcelable(URI);
            parkId = getArguments().getString(PARK_ID);
            position = getArguments().getInt(POSITION);
            latLong = getArguments().getString(LATLONG);
        }
        new NetworkCall().execute();
    }


    private class NetworkCall extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            getCurrentWeather();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            createWeatherview(currentWeather,main,wind,sys);
        }
    }



    public void getCurrentWeather(){

        String apiKey = getContext().getResources().getString(R.string.OWMapiKey);
        String metric = getContext().getResources().getString(R.string.units);
        StringToGPSCordinates stringToGPSCordinates = new StringToGPSCordinates();
        Log.e(TAG, "getCurrentWeather RESPONSE: "+latLong);
        final String gpsCoodinates[] = stringToGPSCordinates.convertToGPS(latLong);
        Call<CurrentWeather> weatherData = OpenWeatherMapApiConnection.getApi().getWeather(gpsCoodinates[0],gpsCoodinates[1],apiKey,metric);
        Response<CurrentWeather> response = null;
        try {
            response = weatherData.execute();
            Log.e(TAG, "getCurrentWeather: RESPONSE: DONE");
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentWeather = response.body().getWeather();
        main = response.body().getMain();
        wind = response.body().getWind();
        sys =  response.body().getSys();


//        OpenWeatherMapApiConnection.getApi().getWeather(gpsCoodinates[0],gpsCoodinates[1],apiKey,metric).enqueue(new Callback<CurrentWeather>() {
//            @Override
//            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
//                Log.e(TAG, "onResponse: RESPONSE");
//                List<Weather> currentWeather;
//                Main main;
//                Wind wind;
//                Sys sys;
//                currentWeather = response.body().getWeather();
//                main = response.body().getMain();
//                wind = response.body().getWind();
//                sys =  response.body().getSys();
//                createWeatherview(currentWeather,main,wind,sys);
//            }
//
//            @Override
//            public void onFailure(Call<CurrentWeather> call, Throwable t) {
//
//            }
//        });
    }

    public void createWeatherview(List<Weather> weather, Main main, Wind wind, Sys sys){
        currentTempTextview.setText(main.getTemp().toString()+"\u00b0"+"F");
        weatherConditionTextview.setText(weather.get(0).getMain());
        minTempTextview.setText(main.getTempMin().toString()+"\u00b0"+"F");
        maxTempTextview.setText(main.getTempMax().toString()+"\u00b0"+"F");
    }



}
