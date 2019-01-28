package com.sjani.usnationalparkguide.Data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(FavParkEntity park);

    @Query("SELECT * FROM favorites")
    LiveData<List<FavParkEntity>> getFavPark();

    @Query("SELECT * FROM favorites")
    Single<List<FavParkEntity>> getFavParkRJ(); // Switch to RxJava

    @Query("DELETE FROM favorites")
    void clearTable();
//    @Query("UPDATE parks SET isFav= :isFav WHERE parkCode = :parkCode")
//    void updateFav(boolean isFav, String parkCode);
}
