package com.sjani.usnationalparkguide.UI.Widget;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sjani.usnationalparkguide.Data.FavDatabase;
import com.sjani.usnationalparkguide.Data.FavParkEntity;
import com.sjani.usnationalparkguide.Models.Weather.CurrentWeather;
import com.sjani.usnationalparkguide.Models.Weather.Main;
import com.sjani.usnationalparkguide.Models.Weather.Sys;
import com.sjani.usnationalparkguide.Models.Weather.Weather;
import com.sjani.usnationalparkguide.Models.Weather.Wind;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.NetworkSync.Weather.OpenWeatherMapApiConnection;
import com.sjani.usnationalparkguide.Utils.StringToGPSCoordinates;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class ParkWidgetService extends IntentService {


    public static final String UPDATE_WIDGET = "update_widget";
    private static final String TAG = ParkWidgetService.class.getSimpleName();
    private static final String NOTIFICATION_CHANNEL_ID = "notification_channel";
    private static FavDatabase db;
    String latLong;
    String parkCode;
    String imgUrl;
    String title;
    Integer distance;
    private FusedLocationProviderClient locationClient;
    private String weatherDetails[];

    public ParkWidgetService() {
        super("ParkWidgetService");
    }

    public static void startActionUpdateWidgets(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(), FavDatabase.class, FavDatabase.DATABASE_NAME).build();
        Intent intent = new Intent(context, ParkWidgetService.class);
        intent.setAction(UPDATE_WIDGET);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        Bitmap largeIcon = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Favorite Updated")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon);

        Notification notification = notificationBuilder.build();
        notificationManager.notify(111, notification);
        startForeground(1, notification);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null || intent.getAction() == null) return;
        if (intent.getAction().equals(UPDATE_WIDGET)) {
            Context context = getApplicationContext();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ParkWidgetProvider.class));

            FavDatabase.getInstance(context).favDoa().getFavParkRJ()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(favParkEntityList -> {
                        // Put your mainThread works here.
                        if (favParkEntityList != null) {
                            if (favParkEntityList.size() == 1) {
                                FavParkEntity favParkEntity = favParkEntityList.get(0);
                                latLong = favParkEntity.getLatLong();
                                parkCode = favParkEntity.getParkCode();
                                imgUrl = favParkEntity.getImage();
                                title = favParkEntity.getPark_name();
                                StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
                                final String gpsCoodinates[] = stringToGPSCoordinates.convertToGPS(latLong);
                                getLastLocation(gpsCoodinates);
                                ParkWidgetProvider.updateAppWidgets(context, appWidgetManager, appWidgetIds, parkCode, imgUrl, title, distance);
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getLastLocation(String gpsCoodinates[]) {


        final Double latitude = Double.parseDouble(gpsCoodinates[0]);
        final Double longitude = Double.parseDouble(gpsCoodinates[1]);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("MapDemoActivity", "****************LOCATION PERMISSIONS DENIED*****************Error trying to get last GPS location");
            return;
        }
        locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // GPS location can be null if GPS is switched off
                if (location != null) {
                    Location targetLocation = new Location("");
                    targetLocation.setLatitude(latitude);
                    targetLocation.setLongitude(longitude);
                    distance = (int) Math.round((targetLocation.distanceTo(location) / 1000) * 0.621371);
                } else {
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MapDemoActivity", "***************LOCATION FAILURE****************Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    public String[] getCurrentWeather(Context mContext, String gpsCoodinates[]) {

        List<Weather> currentWeather;
        Main main;
        Wind wind;
        Sys sys;
        String weatherDetails[] = new String[2];
        String apiKey = mContext.getResources().getString(R.string.OWMapiKey);
        String metric = mContext.getResources().getString(R.string.units);


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
            if (main != null && currentWeather != null && wind != null && sys != null) {
                weatherDetails[0] = String.format(Locale.US, "%d\u00b0F", (Long) Math.round(main.getTemp()));
                weatherDetails[1] = currentWeather.get(0).getMain();
            }
        }
        return weatherDetails;

    }


}
