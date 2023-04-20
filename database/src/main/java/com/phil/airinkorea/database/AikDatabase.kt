package com.phil.airinkorea.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.phil.airinkorea.database.dao.AirDataDao
import com.phil.airinkorea.database.dao.GPSLocationDao
import com.phil.airinkorea.database.dao.LocationDao
import com.phil.airinkorea.database.dao.UserLocationsDao
import com.phil.airinkorea.database.model.AirDataEntity
import com.phil.airinkorea.database.model.GPSLocationEntity
import com.phil.airinkorea.database.model.LocationEntity
import com.phil.airinkorea.database.model.UserLocationEntity

@Database(
    entities = [
        LocationEntity::class,
        GPSLocationEntity::class,
        UserLocationEntity::class,
        AirDataEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DailyForecastTypeConverter::class)
abstract class AIKDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun gpsLocationDao(): GPSLocationDao
    abstract fun userLocationsDao(): UserLocationsDao
    abstract fun airDataDao(): AirDataDao
}
