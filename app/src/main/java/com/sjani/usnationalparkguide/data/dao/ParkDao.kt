package com.sjani.usnationalparkguide.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sjani.usnationalparkguide.data.entity.ParkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(parkEntity: ParkEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(parks: List<ParkEntity>)
    
    @Query("SELECT * FROM parks")
    fun getAllParks(): Flow<List<ParkEntity>>
    
    @Query("SELECT * FROM parks WHERE parkCode = :parkCode")
    fun getPark(parkCode: String): Flow<ParkEntity?>
    
    @Query("DELETE FROM parks")
    suspend fun clearTable()
    
    @Query("UPDATE parks SET isFav = :isFav WHERE parkCode = :parkCode")
    suspend fun updateFav(isFav: Boolean, parkCode: String)
}


