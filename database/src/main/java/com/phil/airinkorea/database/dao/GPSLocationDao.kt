package com.phil.airinkorea.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.database.model.GPSLocationEntity
import com.phil.airinkorea.database.model.UserLocationsEntity
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
    suspend fun updateGPSLocation(enDo: String, enSigungu: String, enEupmyeondong: String, station: String): Boolean

    @Insert(entity = GPSLocationEntity::class)
    suspend fun insertUserLocation(newData: UserLocationsEntity): Boolean

    @Query(
        """
        SELECT en_do,en_sigungu,en_eupmyeondong,station 
        FROM gps_location
        WHERE row_id = 1
        LIMIT 1
        """
    )
    fun getGPSLocation(): Flow<Location>
}