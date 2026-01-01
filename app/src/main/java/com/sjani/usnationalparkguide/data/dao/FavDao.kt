package com.sjani.usnationalparkguide.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sjani.usnationalparkguide.data.entity.FavParkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(favParkEntity: FavParkEntity)
    
    @Query("SELECT * FROM fav_parks")
    fun getAllFavorites(): Flow<List<FavParkEntity>>
    
    @Query("SELECT * FROM fav_parks WHERE park_id = :parkId")
    fun getFavoriteById(parkId: String): Flow<FavParkEntity?>
    
    @Query("SELECT EXISTS(SELECT 1 FROM fav_parks WHERE park_id = :parkId)")
    fun isFavorite(parkId: String): Flow<Boolean>
    
    @Query("DELETE FROM fav_parks WHERE park_id = :parkId")
    suspend fun deleteById(parkId: String)
    
    @Query("DELETE FROM fav_parks")
    suspend fun clearTable()
}

