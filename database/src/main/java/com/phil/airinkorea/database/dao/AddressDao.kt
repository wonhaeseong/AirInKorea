package com.phil.airinkorea.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.phil.airinkorea.database.model.AddressEntity
import kotlinx.coroutines.flow.Flow

/**
 * [AddressEntity]에 접근하기위한 DAOs (Data Access Objects)
 */
@Dao
interface AddressDao {
    @Query("SELECT * FROM addresses WHERE en_address MATCH :input LIMIT :limit")
    fun getMatchAddresses(input: String, limit: Int): Flow<List<AddressEntity>>
}