package com.sjani.usnationalparkguide.Data;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(CampEntity campEntity);

    @Query("SELECT * FROM camps")
    LiveData<List<CampEntity>> getAllCamps();

    @Query("SELECT * FROM camps WHERE camp_id = :campId")
    LiveData<CampEntity> getCamp(String campId);

    @Query("DELETE FROM camps")
    void clearTable();

}
