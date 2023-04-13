package com.phil.airinkorea.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phil.airinkorea.data.model.*


/**
 * 측정소 별 오염 데이터를 가지는 entity
 */
@Entity(tableName = "airData")
data class AirDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "station") val station: String,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "air_level") val airLevel: AirLevel?,
    @ColumnInfo(name = "detail_air_data") val DetailAirData : DetailAirData?,
    @ColumnInfo(name = "daily_forecast") val dailyForecast: List<DailyForecast>?,
    @ColumnInfo(name = "information") val information: String?,
    @ColumnInfo(name = "koreaForecastMapImgUrl") val koreaForecastMapImgUrl: String?,
    @ColumnInfo(name = "koreaModelGif") val koreaModelGif:KoreaForecastModelGif?
)