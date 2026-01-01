package com.sjani.usnationalparkguide.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sjani.usnationalparkguide.data.entity.TrailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(trailEntity: TrailEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(trails: List<TrailEntity>)
    
    @Query("SELECT * FROM trails")
    fun getAllTrails(): Flow<List<TrailEntity>>
    
    @Query("SELECT * FROM trails WHERE trail_id = :trailId")
    fun getTrail(trailId: String): Flow<TrailEntity?>
    
    @Query("DELETE FROM trails")
    suspend fun clearTable()
}


