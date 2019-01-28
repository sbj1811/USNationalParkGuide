package com.sjani.usnationalparkguide.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {AlertEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AlertDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "alerts";

    private static final Object LOCK = new Object();
    private static AlertDatabase sInstance;

    public static AlertDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AlertDatabase.class, AlertDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }


    public abstract AlertDao alertDao();

}
