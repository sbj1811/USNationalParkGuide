package com.sjani.usnationalparkguide.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {FavParkEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FavDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "favorites";

    private static final Object LOCK = new Object();
    private static FavDatabase sInstance;

    public static FavDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), FavDatabase.class, FavDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public RoomDatabase dbExist() {
        return sInstance;
    }

    public abstract FavDao favDoa();

}
