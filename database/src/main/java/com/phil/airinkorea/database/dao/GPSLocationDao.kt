package com.phil.airinkorea.database.dao

import androidx.room.*
import com.phil.airinkorea.database.model.GPSLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GPSLocationDao {
    @Transaction
    suspend fun deleteAndInsertGPSLocation(oldData: GPSLocationEntity, newData: GPSLocationEntity) {
        deleteGPSLocation(oldData)
        insertGPSLocation(newData)
    }
    @Insert(entity = GPSLocationEntity::class)
    suspend fun insertGPSLocation(newData: GPSLocationEntity)

    @Delete
    suspend fun deleteGPSLocation(data:GPSLocationEntity)

    @Query(
        """
        SELECT *
        FROM gps_location
        LIMIT 1
        """
    )
    fun getGPSLocation(): Flow<GPSLocationEntity>
}