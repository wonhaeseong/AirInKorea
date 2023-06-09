package com.phil.airinkorea.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phil.airinkorea.data.database.model.UserLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserLocationsDao {
    @Query(
        """
        SELECT *
        FROM user_locations
        WHERE bookmark = 0
        """
    )
    fun getUserLocationList(): Flow<List<UserLocationEntity>>

    @Insert(entity = UserLocationEntity::class)
    suspend fun insertUserLocation(newData: UserLocationEntity)

    @Delete
    suspend fun deleteUserLocationData(userLocation: UserLocationEntity)

    @Query(
        """
        SELECT *
        FROM user_locations
        WHERE bookmark = 1    
        """
    )
    fun getBookmark(): Flow<UserLocationEntity?>

    @Update
    fun updateUserLocation(userLocationEntity: UserLocationEntity)
}