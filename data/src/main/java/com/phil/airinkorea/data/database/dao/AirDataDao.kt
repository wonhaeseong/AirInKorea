package com.phil.airinkorea.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.phil.airinkorea.data.database.model.AirDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AirDataDao {
    @Upsert(entity = AirDataEntity::class,)
    fun upsertAirData(newData: AirDataEntity)

    @Query(
        """
        SELECT * 
        FROM airData 
        WHERE station in (
            SELECT station
            FROM user_locations 
            WHERE is_gps = 1
        ) 
        """
    )
    fun getAirDataForGPS(): Flow<AirDataEntity?>

    @Query(
        """
        SELECT * 
        FROM airData 
        WHERE station in (
            SELECT station
            FROM user_locations 
            WHERE is_bookmark = 1
        ) 
        """
    )
    fun getAirDataForBookmark(): Flow<AirDataEntity?>

    @Query(
        """
        SELECT * 
        FROM airData 
        WHERE station =:station
        """
    )
    fun getAirDataForCustomLocations(station:String): Flow<AirDataEntity?>
}