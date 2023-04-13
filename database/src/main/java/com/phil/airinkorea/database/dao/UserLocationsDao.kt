package com.phil.airinkorea.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.database.model.UserLocationsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserLocationsDao {
    @Query(
        """
        SELECT en_do,en_sigungu,en_eupmyeondong,station
        FROM user_locations
        """
    )
    fun getAllUserLocation(): Flow<List<Location>>

    @Insert(entity = UserLocationsEntity::class)
    suspend fun insertUserLocation(newData: UserLocationsEntity): Boolean

    @Query(
        """
        UPDATE user_locations SET bookmark = CASE
        WHEN station = :oldBookmarkStation THEN 0 
        WHEN station = :newBookmarkStation THEN 1 
        ELSE bookmark END
        """
    )
    fun updateBookmark(oldBookmarkStation: String, newBookmarkStation: String): Boolean

    @Query(
        """
        DELETE FROM user_locations
        WHERE en_do = :enDo AND en_sigungu = :enSigungu AND en_eupmyeondong = :enEupmyeondong
        """
    )
    suspend fun deleteUserLocationData(enDo: String, enSigungu: String, enEupmyeondong: String): Boolean

}