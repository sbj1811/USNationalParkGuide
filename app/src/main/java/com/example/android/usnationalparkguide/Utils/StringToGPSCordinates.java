package com.example.android.usnationalparkguide.Utils;

import android.util.Log;

import java.util.List;

public class StringToGPSCordinates {


    public StringToGPSCordinates() {
    }

    public String[] convertToGPS (String latlong) {
        String splitGPS[] = new String[2];
        String asd[] = latlong.split(", ");
        Log.e("StringToGPSCordinates", "convertToGPS: "+asd);
        splitGPS[0] = asd[0].replaceAll("[\\s+a-zA-Z :]","");
        splitGPS[1] = asd[1].replaceAll("[\\s+a-zA-Z :]","");
        Log.e("StringToGPSCordinates", "convertToGPS: LAT: "+splitGPS[0]+" LONG: "+splitGPS[1]);
        return splitGPS;
    }
}
