package com.phil.airinkorea.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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
            WHERE is_selected = 1
        ) 
        """
    )
    fun getAirDataForSelectedUserLocations(): Flow<AirDataEntity?>
}