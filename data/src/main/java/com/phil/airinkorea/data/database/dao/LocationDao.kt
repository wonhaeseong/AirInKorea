package com.phil.airinkorea.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.phil.airinkorea.data.database.model.LocationEntity
import kotlinx.coroutines.flow.Flow

/**
 * [LocationEntity]에 접근하기위한 DAO
 */
@Dao
interface LocationDao {
    @Query(
        """
        SELECT *
        FROM locations
        WHERE en_do LIKE '%' || :query || '%'
        OR en_sigungu LIKE '%' || :query || '%'
        OR en_eupmyeondong LIKE '%' || :query || '%'
        """
    )
    fun searchLocation(query: String?): Flow<List<LocationEntity>>

    @Query(
        """
        SELECT *
        FROM locations
        WHERE en_eupmyeondong LIKE '%' || :query || '%'
        """
    )
    fun getMatchLocationByEupmyeondong(query: String): LocationEntity
}