package com.phil.airinkorea.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phil.airinkorea.database.dao.AddressDao
import com.phil.airinkorea.database.model.LocationEntity

@Database(
    entities = [LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AIKDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
}

