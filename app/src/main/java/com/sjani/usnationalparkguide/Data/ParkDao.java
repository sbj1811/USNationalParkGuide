package com.sjani.usnationalparkguide.Data;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ParkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(ParkEntity parkEntity);

    @Query("SELECT * FROM parks")
    LiveData<List<ParkEntity>> getAllParks();

    @Query("SELECT * FROM parks WHERE parkCode = :parkCode")
    LiveData<ParkEntity> getPark(String parkCode);

    @Query("DELETE FROM parks")
    void clearTable();

    @Query("UPDATE parks SET isFav= :isFav WHERE parkCode = :parkCode")
    void updateFav(boolean isFav, String parkCode);
}
