package com.sjani.usnationalparkguide.Data;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(AlertEntity alertEntity);

    @Query("SELECT * FROM alerts")
    LiveData<List<AlertEntity>> getAllAlerts();

    @Query("DELETE FROM alerts")
    void clearTable();

}
