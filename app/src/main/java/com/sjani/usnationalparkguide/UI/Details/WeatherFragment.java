package com.sjani.usnationalparkguide.UI.Details;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sjani.usnationalparkguide.Models.Weather.CurrentWeather;
import com.sjani.usnationalparkguide.Models.Weather.Main;
import com.sjani.usnationalparkguide.Models.Weather.Sys;
import com.sjani.usnationalparkguide.Models.Weather.Weather;
import com.sjani.usnationalparkguide.Models.Weather.Wind;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.FactoryUtils;
import com.sjani.usnationalparkguide.Utils.NetworkSync.Weather.OpenWeatherMapApiConnection;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;
import com.sjani.usnationalparkguide.Utils.WeatherUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;


public class WeatherFragment extends Fragment {

    private static final String TAG = WeatherFragment.class.getSimpleName();
    private static final String LATLONG = "latlong";
    private static final String PARK_CODE = "park_code";
    private static final String IS_FAV = "is_fav";

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

    private String parkCode;
    private List<Weather> currentWeather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private Context mContext;
    private boolean isFromFavNav;
    private String latLong;


    public WeatherFragment() {
        // Required empty public constructor
    }


    public static WeatherFragment newInstance(String parkCode, boolean isFromFavNav, String latLong) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(PARK_CODE, parkCode);
        args.putBoolean(IS_FAV, isFromFavNav);
        args.putString(LATLONG, latLong);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            parkCode = savedInstanceState.getString(PARK_CODE);
            latLong = savedInstanceState.getString(LATLONG);
            isFromFavNav = savedInstanceState.getBoolean(IS_FAV);
        } else {
            if (getArguments() != null) {
                parkCode = getArguments().getString(PARK_CODE);
                latLong = getArguments().getString(LATLONG);
                isFromFavNav = getArguments().getBoolean(IS_FAV);
            }
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
        String apiKey = getResources().getString(R.string.NPSapiKey);
        String trailApiKey = getResources().getString(R.string.HPapiKey);
        String fields = getResources().getString(R.string.fields_cg);
        DetailViewModelFactory factory = FactoryUtils.provideDVMFactory(this.getActivity().getApplicationContext(), parkCode, "", "", apiKey, trailApiKey, fields, latLong);
        DetailViewModel viewModel = ViewModelProviders.of(getActivity(), factory).get(DetailViewModel.class);
        if (!isFromFavNav) {
            viewModel.getPark().observe(this, Park -> {
                parkCode = Park.getParkCode();
                Observable.fromCallable(() -> {
                    getCurrentWeather(Park.getLatLong());
                    return false;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((result) -> {
                            createWeatherview(currentWeather, main, wind, sys);
                        });
            });
        } else {
            viewModel.getfavpark().observe(this, Park -> {
                if (Park.size() == 1) {
                    parkCode = Park.get(0).getParkCode();
                    Observable.fromCallable(() -> {
                        getCurrentWeather(Park.get(0).getLatLong());
                        return false;
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((result) -> {
                                createWeatherview(currentWeather, main, wind, sys);
                            });
                }
            });
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getCurrentWeather(String latLong) {

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
        progressBar.setVisibility(View.INVISIBLE);
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
        outState.putString(PARK_CODE, parkCode);
        outState.putString(LATLONG, latLong);
        outState.putBoolean(IS_FAV, isFromFavNav);
        super.onSaveInstanceState(outState);
    }

}
