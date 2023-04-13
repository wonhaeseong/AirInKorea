package com.phil.airinkorea.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.phil.airinkorea.data.model.DailyForecast
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
class TypeConverter
 {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun fromDetailAirDataToString(detailAirData: DetailAirData): String =
        json.encodeToString(detailAirData)

    @TypeConverter
    fun fromStringToDetailAirData(detailAirDataJson: String): DetailAirData =
        Json.decodeFromString(detailAirDataJson)

    @TypeConverter
    fun fromDailyForecastToString(dailyForecastList: List<DailyForecast>): String =
        json.encodeToString(dailyForecastList)

    @TypeConverter
    fun fromStringToDailyForecast(dailyForecastListJson: String): List<DailyForecast> =
        Json.decodeFromString(dailyForecastListJson)

    @TypeConverter
    fun fromKoreaForecastModelGifToString(koreaForecastModelGif: KoreaForecastModelGif): String =
        json.encodeToString(koreaForecastModelGif)

    @TypeConverter
    fun fromStringToKoreaForecastModelGif(koreaForecastModelGifJson: String): KoreaForecastModelGif =
        Json.decodeFromString(koreaForecastModelGifJson)
}
