package com.sjani.usnationalparkguide.UI.Details;

import android.app.LoaderManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.sjani.usnationalparkguide.Models.Weather.CurrentWeather;
import com.sjani.usnationalparkguide.Models.Weather.Main;
import com.sjani.usnationalparkguide.Models.Weather.Sys;
import com.sjani.usnationalparkguide.Models.Weather.Weather;
import com.sjani.usnationalparkguide.Models.Weather.Wind;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.NetworkSync.Weather.OpenWeatherMapApiConnection;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;
import com.sjani.usnationalparkguide.Utils.WeatherUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;


public class WeatherFragment extends Fragment {

    private static final String TAG = WeatherFragment.class.getSimpleName();
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final int LOADER_ID = 12;


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
    @BindView(R.id.weather_condition_image)
    ImageView weatherConditionImageView;
    @BindView(R.id.weather_loading_indicator)
    ProgressBar progressBar;

    private Uri uri;
    private String parkId;
    private int position;
    private String latLong;
    private List<Weather> currentWeather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private Context mContext;


    public WeatherFragment() {
        // Required empty public constructor
    }


    public static WeatherFragment newInstance(Uri uri, String parkId, int position, String latlong) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI, uri);
        args.putString(PARK_ID, parkId);
        args.putInt(POSITION, position);
        args.putString(LATLONG, latlong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            uri = savedInstanceState.getParcelable(URI);
            parkId = savedInstanceState.getString(PARK_ID);
            position = savedInstanceState.getInt(POSITION);
            latLong = savedInstanceState.getString(LATLONG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (getArguments() != null) {
            uri = getArguments().getParcelable(URI);
            parkId = getArguments().getString(PARK_ID);
            position = getArguments().getInt(POSITION);
            latLong = getArguments().getString(LATLONG);
        }
        new NetworkCall().execute();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getCurrentWeather() {

        String apiKey = mContext.getResources().getString(R.string.OWMapiKey);
        String metric = mContext.getResources().getString(R.string.units);
        StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
        final String gpsCoodinates[] = stringToGPSCoordinates.convertToGPS(latLong);
        Call<CurrentWeather> weatherData = OpenWeatherMapApiConnection.getApi().getWeather(gpsCoodinates[0], gpsCoodinates[1], apiKey, metric);
        Response<CurrentWeather> response = null;
        try {
            response = weatherData.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            currentWeather = response.body().getWeather();
            main = response.body().getMain();
            wind = response.body().getWind();
            sys = response.body().getSys();
        }
    }

    public void createWeatherview(List<Weather> weather, Main main, Wind wind, Sys sys) {
        if (main != null && weather != null && wind != null && sys != null) {
            Double windspeed = wind.getSpeed();
            Double windDegree = wind.getDeg();
            String windInfo = WeatherUtils.getFormattedWind(mContext, windspeed, windDegree);
            int weatherIconId = WeatherUtils.getResourceIdForWeatherCondition(weather.get(0).getId());

            Long sunriseTimeVal = sys.getSunrise();
            Long sunsetTimeVal = sys.getSunset();

            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
            Date dateSunrise = new Date(sunriseTimeVal * 1000);
            Date dateSunset = new Date(sunsetTimeVal * 1000);

            String sunriseTime = sdf.format(dateSunrise);
            String sunsetTime = sdf.format(dateSunset);

            sunriseTimeTextview.setText(sunriseTime);
            sunsetTimeTextview.setText(sunsetTime);
            weatherConditionImageView.setImageResource(weatherIconId);
//            loadImage(Glide.with(getActivity()), weatherIconId, weatherConditionImageView);
            Picasso.with(mContext)
                    .load(weatherIconId)
                    .into(weatherConditionImageView);
            windTextview.setText(windInfo);
            String humidityInfo = String.format(Locale.US, "%d", main.getHumidity()) + "\u0025";
            humidityTextview.setText(humidityInfo);
            currentTempTextview.setText(String.format(Locale.US, "%d\u00b0F", (Long) Math.round(main.getTemp())));
            weatherConditionTextview.setText(weather.get(0).getMain());
            minTempTextview.setText(String.format(Locale.US, "%d\u00b0F", (Long) Math.round(main.getTempMin())));
            maxTempTextview.setText(String.format(Locale.US, "%d\u00b0F", (Long) Math.round(main.getTempMax())));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

//    static void loadImage(RequestManager glide, int weatherIconId, ImageView view) {
//        glide.load(weatherIconId).fitCenter().into(view);
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(URI, uri);
        outState.putString(PARK_ID, parkId);
        outState.putInt(POSITION, position);
        outState.putString(LATLONG, latLong);
        super.onSaveInstanceState(outState);
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
            createWeatherview(currentWeather, main, wind, sys);
        }
    }
}
