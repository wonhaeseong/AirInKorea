package com.phil.airinkorea.data.database.dao

import androidx.room.*
import com.phil.airinkorea.data.database.model.GPSLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GPSLocationDao {
    @Query(
        """
        UPDATE gps_location 
        SET en_do =:enDo, en_sigungu =:enSigungu, en_eupmyeondong=:enEupmyeondong, station=:station
        WHERE row_id = 1    
        """
    )
    suspend fun updateGPSLocation(
        enDo: String,
        enSigungu: String,
        enEupmyeondong: String,
        station: String
    )

    @Insert(entity = GPSLocationEntity::class)
    suspend fun insertGPSLocation(newData: GPSLocationEntity)

    @Query(
        """
        SELECT *
        FROM gps_location
        LIMIT 1
        """
    )
    fun getGPSLocation(): Flow<GPSLocationEntity?>

    @Query(
        """
        SELECT EXISTS(SELECT * FROM gps_location)
        """
    )
    suspend fun isExists(): Boolean

    @Query(
        """
        DELETE FROM gps_location
        WHERE row_id = 1
        """
    )
    suspend fun deleteGPSLocation()
}