package com.sjani.usnationalparkguide.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class CampContract {

    public static final String CONTENT_AUTHORITY = "com.sjani.usnationalparkguide";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // All possible paths for accessing data in this contract
    public static final String PATH_CAMPS = "allcamps";

    public static final class CampEntry implements BaseColumns {

        public static final Uri CONTENT_URI_CAMP = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CAMPS)
                .build();


        // Table names
        public static final String TABLE_NAME_CAMP = "camp";


        public static final String _ID =  BaseColumns._ID;
        public static final String COLUMN_CAMP_ID = "camp_id";
        public static final String COLUMN_CAMP_NAME = "camp_name";
        public static final String COLUMN_CAMP_DESCRIPTION = "description";
        public static final String COLUMN_CAMP_PARKCODE = "parkCode";
        public static final String COLUMN_CAMP_ADDRESSS = "addresses";
        public static final String COLUMN_CAMP_LATLONG = "latLong";       
        public static final String COLUMN_CAMP_CELLRECEP = "cellPhoneReception";
        public static final String COLUMN_CAMP_SHOWERS = "showers";
        public static final String COLUMN_CAMP_INTERNET = "internetConnectivity";
        public static final String COLUMN_CAMP_TOILET = "toilets";
        public static final String COLUMN_CAMP_WHEELCHAIR = "wheelchairAccess";
        public static final String COLUMN_CAMP_RESERVURL = "reservationsUrl";
        public static final String COLUMN_CAMP_DIRECTIONURL = "directionsUrl";

        

    }

}
