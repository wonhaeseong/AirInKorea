package com.phil.airinkorea.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.phil.airinkorea.data.database.dao.AirDataDao
import com.phil.airinkorea.data.database.dao.LocationDao
import com.phil.airinkorea.data.database.dao.UserLocationsDao
import com.phil.airinkorea.data.database.model.AirDataEntity
import com.phil.airinkorea.data.database.model.LocationEntity
import com.phil.airinkorea.data.database.model.UserLocationEntity

@Database(
    entities = [
        LocationEntity::class,
        UserLocationEntity::class,
        AirDataEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DailyForecastTypeConverter::class)
abstract class AIKDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun userLocationsDao(): UserLocationsDao
    abstract fun airDataDao(): AirDataDao
}
