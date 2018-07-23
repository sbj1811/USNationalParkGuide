package com.sjani.usnationalparkguide.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TrailContract {

    public static final String CONTENT_AUTHORITY = "com.sjani.usnationalparkguide";
    // All possible paths for accessing data in this contract
    public static final String PATH_TRAILS = "alltrails";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class TrailEntry implements BaseColumns {

        public static final Uri CONTENT_URI_TRAIL = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TRAILS)
                .build();


        // Table names
        public static final String TABLE_NAME_TRAIL = "trail";


        public static final String _ID =  BaseColumns._ID;
        public static final String COLUMN_TRAIL_ID = "trail_id";
        public static final String COLUMN_TRAIL_NAME = "trail_name";
        public static final String COLUMN_TRAIL_SUMMARY = "summary";
        public static final String COLUMN_TRAIL_DIFFICULTY = "difficulty";
        public static final String COLUMN_TRAIL_IMAGE_SMALL = "image_small";
        public static final String COLUMN_TRAIL_IMAGE_MED = "image_med";
        public static final String COLUMN_TRAIL_LENGTH = "length";       
        public static final String COLUMN_TRAIL_ASCENT = "ascent";
        public static final String COLUMN_TRAIL_LAT = "lat";
        public static final String COLUMN_TRAIL_LONG = "long";
        public static final String COLUMN_TRAIL_LOCATION = "location";
        public static final String COLUMN_TRAIL_CONDITION = "condition";
        public static final String COLUMN_TRAIL_MOREINFO = "more_info";
        

    }

}
