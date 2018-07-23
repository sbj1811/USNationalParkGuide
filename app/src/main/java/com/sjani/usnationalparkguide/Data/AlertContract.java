package com.sjani.usnationalparkguide.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class AlertContract {

    public static final String CONTENT_AUTHORITY = "com.sjani.usnationalparkguide";
    // All possible paths for accessing data in this contract
    public static final String PATH_ALERTS = "allalerts";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class AlertEntry implements BaseColumns {

        public static final Uri CONTENT_URI_ALERT = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_ALERTS)
                .build();


        // Table names
        public static final String TABLE_NAME_ALERT = "alert";


        public static final String _ID =  BaseColumns._ID;
        public static final String COLUMN_ALERT_ID = "alert_id";
        public static final String COLUMN_ALERT_NAME = "alert_name";
        public static final String COLUMN_ALERT_DESCRIPTION = "description";
        public static final String COLUMN_ALERT_PARKCODE = "parkCode";
        public static final String COLUMN_ALERT_CATEGORY = "category";



    }

}
