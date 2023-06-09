package com.phil.airinkorea.data.database

import androidx.room.TypeConverter
import com.phil.airinkorea.data.database.model.DailyForecastEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DailyForecastTypeConverter {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun fromDailyForecastToString(dailyForecastList: ArrayList<DailyForecastEntity>): String {
        return json.encodeToString(dailyForecastList)
    }

    @TypeConverter
    fun fromStringToDailyForecast(value: String): ArrayList<DailyForecastEntity> {
        return json.decodeFromString(value)
    }
}
