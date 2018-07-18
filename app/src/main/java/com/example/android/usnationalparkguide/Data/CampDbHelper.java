package com.example.android.usnationalparkguide.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.usnationalparkguide.Data.CampContract.CampEntry;

public class CampDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "camp.db";
    private static final int DATABASE_VERSION = 1;

    public CampDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static String createTable(String tableName){
        return "CREATE TABLE " + tableName + " (" +
                CampEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CampEntry.COLUMN_CAMP_ID + " TEXT NOT NULL, " +
                CampEntry.COLUMN_CAMP_NAME + " TEXT NOT NULL, " +
                CampEntry.COLUMN_CAMP_DESCRIPTION + " TEXT, " +
                CampEntry.COLUMN_CAMP_PARKCODE + " TEXT, " +
                CampEntry.COLUMN_CAMP_ADDRESSS + " TEXT, " +
                CampEntry.COLUMN_CAMP_LATLONG + " TEXT, " +
                CampEntry.COLUMN_CAMP_CELLRECEP + " TEXT, " +
                CampEntry.COLUMN_CAMP_SHOWERS + " TEXT, " +
                CampEntry.COLUMN_CAMP_INTERNET + " TEXT, " +
                CampEntry.COLUMN_CAMP_TOILET + " TEXT, " +
                CampEntry.COLUMN_CAMP_WHEELCHAIR + " TEXT, " +
                CampEntry.COLUMN_CAMP_RESERVURL + " TEXT, " +
                CampEntry.COLUMN_CAMP_DIRECTIONURL + " TEXT, " +
                " UNIQUE (" + CampEntry.COLUMN_CAMP_ID + ") ON CONFLICT REPLACE);";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createTable(CampEntry.TABLE_NAME_CAMP));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CampEntry.TABLE_NAME_CAMP);
        onCreate(sqLiteDatabase);
    }

}
