package com.phil.airinkorea.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.phil.airinkorea.database.model.AddressEntity
import kotlinx.coroutines.flow.Flow

/**
 * [AddressEntity]에 접근하기위한 DAOs (Data Access Objects)
 */
@Dao
interface AddressDao {
    @Query("SELECT * FROM address WHERE en_address Like '%'||:search||'%' LIMIT 10")
    suspend fun getAddresses(search: String): List<AddressEntity>
}