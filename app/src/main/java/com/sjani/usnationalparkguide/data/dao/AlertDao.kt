package com.sjani.usnationalparkguide.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sjani.usnationalparkguide.data.entity.AlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(alertEntity: AlertEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(alerts: List<AlertEntity>)
    
    @Query("SELECT * FROM alerts")
    fun getAllAlerts(): Flow<List<AlertEntity>>
    
    @Query("SELECT * FROM alerts WHERE alert_id = :alertId")
    fun getAlert(alertId: String): Flow<AlertEntity?>
    
    @Query("DELETE FROM alerts")
    suspend fun clearTable()
}


