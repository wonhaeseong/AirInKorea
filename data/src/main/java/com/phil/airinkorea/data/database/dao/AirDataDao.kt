package com.phil.airinkorea.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.phil.airinkorea.data.database.model.AirDataEntity
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
        EXISTS(SELECT 1 FROM airData WHERE station = :station)
        """
    )
    fun isStationExist(station: String): Boolean
}