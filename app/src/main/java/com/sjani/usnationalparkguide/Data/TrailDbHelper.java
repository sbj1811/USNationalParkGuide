package com.sjani.usnationalparkguide.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sjani.usnationalparkguide.Data.TrailContract.TrailEntry;

public class TrailDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trail.db";
    private static final int DATABASE_VERSION = 1;

    public TrailDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static String createTable(String tableName){
        return "CREATE TABLE " + tableName + " (" +
                TrailEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TrailEntry.COLUMN_TRAIL_ID + " TEXT NOT NULL, " +
                TrailEntry.COLUMN_TRAIL_NAME + " TEXT NOT NULL, " +
                TrailEntry.COLUMN_TRAIL_SUMMARY + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_DIFFICULTY + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_IMAGE_SMALL + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_IMAGE_MED + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_LENGTH + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_ASCENT + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_LAT + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_LONG + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_LOCATION + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_CONDITION + " TEXT, " +
                TrailEntry.COLUMN_TRAIL_MOREINFO + " TEXT, " +
                " UNIQUE (" + TrailEntry.COLUMN_TRAIL_ID + ") ON CONFLICT REPLACE);";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createTable(TrailEntry.TABLE_NAME_TRAIL));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TrailEntry.TABLE_NAME_TRAIL);
        onCreate(sqLiteDatabase);
    }

}
