package com.sjani.usnationalparkguide.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sjani.usnationalparkguide.Data.ParkContract.ParkEntry;

public class ParkDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "park.db";
    private static final int DATABASE_VERSION = 1;

    public ParkDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static String createTable(String tableName){
        return "CREATE TABLE " + tableName + " (" +
                ParkEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ParkEntry.COLUMN_PARK_ID + " TEXT NOT NULL, " +
                ParkEntry.COLUMN_PARK_NAME + " TEXT NOT NULL, " +
                ParkEntry.COLUMN_PARK_STATES + " TEXT NOT NULL, " +
                ParkEntry.COLUMN_PARK_CODE + " TEXT NOT NULL, " +
                ParkEntry.COLUMN_PARK_LATLONG + " TEXT," +
                ParkEntry.COLUMN_PARK_DESCRIPTION + " TEXT, " +
                ParkEntry.COLUMN_PARK_DESIGNATION + " TEXT, " +
                ParkEntry.COLUMN_PARK_ADDRESS + " TEXT, " +
                ParkEntry.COLUMN_PARK_PHONE + " TEXT, " +
                ParkEntry.COLUMN_PARK_EMAIL + " TEXT, " +
                ParkEntry.COLUMN_PARK_IMAGE + " TEXT, " +
                " UNIQUE (" + ParkEntry.COLUMN_PARK_ID + ") ON CONFLICT REPLACE);";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createTable(ParkEntry.TABLE_NAME_PARKS));
        sqLiteDatabase.execSQL(createTable(ParkEntry.TABLE_NAME_FAVORITES));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ParkEntry.TABLE_NAME_PARKS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ParkEntry.TABLE_NAME_FAVORITES);
        onCreate(sqLiteDatabase);
    }

}
