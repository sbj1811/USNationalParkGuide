package com.sjani.usnationalparkguide.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {ParkEntity.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ParkDatabase extends RoomDatabase {

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE parks "
                    + " ADD COLUMN isFAV BOOLEAN");
        }
    };
    private static final String DATABASE_NAME = "parks";
    private static final Object LOCK = new Object();
    private static ParkDatabase sInstance;

    public static ParkDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), ParkDatabase.class, ParkDatabase.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract ParkDao parkDoa();
}
