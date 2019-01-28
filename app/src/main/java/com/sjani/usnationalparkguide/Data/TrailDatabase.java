package com.sjani.usnationalparkguide.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TrailEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TrailDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "trails";

    private static final Object LOCK = new Object();
    private static TrailDatabase sInstance;

    public static TrailDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), TrailDatabase.class, TrailDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }


    public abstract TrailDao trailDao();

}
