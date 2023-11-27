package com.phil.airinkorea.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.phil.airinkorea.data.database.model.UserLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserLocationsDao {
    @Query(
        """
        SELECT *
        FROM user_locations
        WHERE is_bookmark = 0 AND is_gps = 0
        """
    )
    fun getCustomLocationListStream(): Flow<List<UserLocationEntity>>

    @Query(
        """
        SELECT *
        FROM user_locations
        WHERE is_bookmark = 0 AND is_gps = 0
        """
    )
    fun getCustomLocationList(): List<UserLocationEntity>

    @Query(
        """
        SELECT *
        FROM user_locations
        WHERE is_gps = 1
        """
    )
    fun getGPSLocationStream(): Flow<UserLocationEntity?>

    @Query(
        """
        SELECT *
        FROM user_locations
        WHERE is_bookmark = 1    
        """
    )
    fun getBookmarkStream(): Flow<UserLocationEntity?>

    @Insert(entity = UserLocationEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertUserLocation(newData: UserLocationEntity)

    @Delete
    fun deleteUserLocation(userLocation: UserLocationEntity)

    @Update
    fun updateUserLocation(userLocationEntity: UserLocationEntity)

    @Query(
        """
        UPDATE user_locations
        SET en_do = :enDo, 
        en_sigungu =:sigungu, 
        en_eupmyeondong=:eupmyeondong,
        station = :station
        WHERE is_gps = 1
        """
    )
    fun updateGPSLocation(enDo: String, sigungu: String, eupmyeondong: String, station: String)

    @Query(
        """
        SELECT EXISTS(
                SELECT * 
                FROM user_locations 
                WHERE is_gps = 1 
                )
        """
    )
    fun isGPSExist(): Boolean

}