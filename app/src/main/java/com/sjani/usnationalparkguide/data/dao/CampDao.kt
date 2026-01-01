package com.sjani.usnationalparkguide.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sjani.usnationalparkguide.data.entity.CampEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CampDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(campEntity: CampEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(camps: List<CampEntity>)
    
    @Query("SELECT * FROM camps")
    fun getAllCamps(): Flow<List<CampEntity>>
    
    @Query("SELECT * FROM camps WHERE camp_id = :campId")
    fun getCamp(campId: String): Flow<CampEntity?>
    
    @Query("DELETE FROM camps")
    suspend fun clearTable()
}


