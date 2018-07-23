package com.sjani.usnationalparkguide.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ParkContract {

    public static final String CONTENT_AUTHORITY = "com.sjani.usnationalparkguide";
    // All possible paths for accessing data in this contract
    public static final String PATH_PARKS = "allparks";
    public static final String PATH_FAVORITES = "favorites";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class ParkEntry implements BaseColumns {

        public static final Uri CONTENT_URI_PARKS = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PARKS)
                .build();

        public static final Uri CONTENT_URI_FAVORITES = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITES)
                .build();

        // Table names
        public static final String TABLE_NAME_PARKS = "park";
        public static final String TABLE_NAME_FAVORITES = "favorite";

        public static final String _ID =  BaseColumns._ID;
        public static final String COLUMN_PARK_ID = "park_id";
        public static final String COLUMN_PARK_NAME = "park_name";
        public static final String COLUMN_PARK_STATES = "states";
        public static final String COLUMN_PARK_CODE = "parkCode";
        public static final String COLUMN_PARK_LATLONG = "latLong";
        public static final String COLUMN_PARK_DESCRIPTION = "description";
        public static final String COLUMN_PARK_DESIGNATION = "designation";
        public static final String COLUMN_PARK_ADDRESS = "address";
        public static final String COLUMN_PARK_PHONE = "phone";
        public static final String COLUMN_PARK_EMAIL = "email";
        public static final String COLUMN_PARK_IMAGE = "image";


    }

}
