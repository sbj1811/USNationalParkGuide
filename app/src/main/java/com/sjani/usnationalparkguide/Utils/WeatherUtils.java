package com.sjani.usnationalparkguide.Utils;

import android.content.Context;
import android.util.Log;

import com.sjani.usnationalparkguide.R;

public class WeatherUtils {

    private static final String TAG = WeatherUtils.class.getSimpleName();

    public static String getFormattedWind(Context context, Double windSpeed, Double degrees) {

        int windFormat = R.string.format_wind_mph;
        Double WindSpeed = .621371192237334f * windSpeed;

        String direction = "";
        if (degrees == null) {
            direction = "";
        } else if (degrees >= 337.5 || degrees < 22.5) {
            direction = "N";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            direction = "NE";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            direction = "E";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            direction = "SE";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            direction = "S";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            direction = "SW";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            direction = "W";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            direction = "NW";
        }
        return String.format(context.getString(windFormat), WindSpeed, direction);
    }

    public static int getResourceIdForWeatherCondition(int weatherId) {

        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_thunderstrom;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_mist;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.ic_thunderstrom;
        } else if (weatherId == 800) {
            return R.drawable.ic_sun;
        } else if (weatherId == 801) {
            return R.drawable.ic_partly_cloudy;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_clouds;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.ic_thunderstrom;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.ic_thunderstrom;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.ic_sun;
        }
        return R.drawable.ic_sun;
    }


}
