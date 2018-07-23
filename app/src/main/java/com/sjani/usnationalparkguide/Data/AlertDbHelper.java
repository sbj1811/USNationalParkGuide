package com.sjani.usnationalparkguide.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlertDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alert.db";
    private static final int DATABASE_VERSION = 1;

    public AlertDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static String createTable(String tableName){
        return "CREATE TABLE " + tableName + " (" +
                AlertContract.AlertEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AlertContract.AlertEntry.COLUMN_ALERT_ID + " TEXT NOT NULL, " +
                AlertContract.AlertEntry.COLUMN_ALERT_NAME + " TEXT NOT NULL, " +
                AlertContract.AlertEntry.COLUMN_ALERT_DESCRIPTION + " TEXT, " +
                AlertContract.AlertEntry.COLUMN_ALERT_PARKCODE + " TEXT, " +
                AlertContract.AlertEntry.COLUMN_ALERT_CATEGORY + " TEXT, " +
                " UNIQUE (" + AlertContract.AlertEntry.COLUMN_ALERT_ID + ") ON CONFLICT REPLACE);";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createTable(AlertContract.AlertEntry.TABLE_NAME_ALERT));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AlertContract.AlertEntry.TABLE_NAME_ALERT);
        onCreate(sqLiteDatabase);
    }
}
