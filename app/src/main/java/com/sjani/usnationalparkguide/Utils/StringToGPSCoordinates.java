package com.sjani.usnationalparkguide.Utils;

public class StringToGPSCoordinates {


    public StringToGPSCoordinates() {
    }

    public String[] convertToGPS(String latlong) {
        String splitGPS[] = new String[2];
        if (latlong.equals("")) {
            splitGPS[0] = "1";
            splitGPS[1] = "1";
        } else {
            String asd[] = latlong.split(", ");
            splitGPS[0] = asd[0].replaceAll("[\\s+a-zA-Z :]", "");
            splitGPS[1] = asd[1].replaceAll("[\\s+a-zA-Z :]", "");
        }
        return splitGPS;
    }
}
