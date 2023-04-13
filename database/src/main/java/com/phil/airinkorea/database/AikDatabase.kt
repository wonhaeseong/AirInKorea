package com.phil.airinkorea.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phil.airinkorea.database.dao.GPSLocationDao
import com.phil.airinkorea.database.dao.LocationDao
import com.phil.airinkorea.database.dao.UserLocationsDao
import com.phil.airinkorea.database.model.AirDataEntity
import com.phil.airinkorea.database.model.GPSLocationEntity
import com.phil.airinkorea.database.model.LocationEntity

@Database(
    entities = [
        LocationEntity::class,
        GPSLocationEntity::class,
        AirDataEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AIKDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun gpsLocationDao(): GPSLocationDao
    abstract fun userLocationsDao(): UserLocationsDao
}



