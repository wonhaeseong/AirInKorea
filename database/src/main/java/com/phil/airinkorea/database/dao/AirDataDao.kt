package com.phil.airinkorea.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phil.airinkorea.database.model.AirDataEntity
import com.phil.airinkorea.domain.model.KoreaForecastModelGif
import kotlinx.coroutines.flow.Flow

@Dao
interface AirDataDao {
    @Insert(
        entity = AirDataEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAirData(newData: AirDataEntity)

    @Query(
        """
        SELECT 
        *
        FROM airData
        WHERE station = :station
        """
    )
    fun getAirData(station: String): Flow<AirDataEntity>

    @Query(
        """
        SELECT
        pm10GifUrl, pm25GifUrl
        FROM airData
        WHERE station = :station
        """
    )
    suspend fun getKoreaForecastModelGif(station: String): KoreaForecastModelGif

    @Query(
        """
        SELECT 
        EXISTS(SELECT 1 FROM airData WHERE station = :station)
        """
    )
    fun isStationExist(station: String): Boolean
}