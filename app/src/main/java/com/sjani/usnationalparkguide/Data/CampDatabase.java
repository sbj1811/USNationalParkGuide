package com.sjani.usnationalparkguide.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {CampEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CampDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "camps";

    private static final Object LOCK = new Object();
    private static CampDatabase sInstance;

    public static CampDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), CampDatabase.class, CampDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }


    public abstract CampDao campDao();

}
