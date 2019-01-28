package com.sjani.usnationalparkguide.Data;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TrailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(TrailEntity trailEntity);

    @Query("SELECT * FROM trails")
    LiveData<List<TrailEntity>> getAllTrails();

    @Query("SELECT * FROM trails WHERE trail_id = :trailId")
    LiveData<TrailEntity> getTrail(String trailId);

    @Query("DELETE FROM trails")
    void clearTable();
}
