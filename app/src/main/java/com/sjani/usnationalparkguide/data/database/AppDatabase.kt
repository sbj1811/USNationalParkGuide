package com.sjani.usnationalparkguide.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sjani.usnationalparkguide.data.dao.*
import com.sjani.usnationalparkguide.data.entity.*

@Database(
    entities = [
        ParkEntity::class,
        FavParkEntity::class,
        TrailEntity::class,
        CampEntity::class,
        AlertEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun parkDao(): ParkDao
    abstract fun favDao(): FavDao
    abstract fun trailDao(): TrailDao
    abstract fun campDao(): CampDao
    abstract fun alertDao(): AlertDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "park_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
