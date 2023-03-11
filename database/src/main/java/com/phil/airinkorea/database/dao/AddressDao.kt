package com.phil.airinkorea.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.phil.airinkorea.database.model.LocationEntity
import com.phil.airinkorea.database.model.LocationEntityFts
import kotlinx.coroutines.flow.Flow

/**
 * [AddressEntity]에 접근하기위한 DAOs (Data Access Objects)
 */
@Dao
interface AddressDao {
    @Query(
        """
        SELECT *
        FROM locations
        WHERE en_do LIKE '%' || :query || '%'
        OR en_sigungu LIKE '%' || :query || '%'
        OR en_eupmyeondong LIKE '%' || :query || '%'
        """
    )
    suspend fun getAddresses(query: String?): List<LocationEntity>

}