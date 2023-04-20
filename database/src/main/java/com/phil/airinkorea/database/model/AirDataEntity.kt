package com.phil.airinkorea.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phil.airinkorea.domain.model.AirData
import com.phil.airinkorea.domain.model.AirLevel
import com.phil.airinkorea.domain.model.DailyForecast
import com.phil.airinkorea.domain.model.DetailAirData
import kotlinx.serialization.Serializable


/**
 * 측정소 별 오염 데이터를 가지는 entity
 */
@Entity(tableName = "airData")
data class AirDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "station") val station: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "air_level") val airLevel: AirLevel,
    @Embedded val detailAirDataEntity: DetailAirDataEntity,
    @ColumnInfo(name = "daily_forecast") val dailyForecastListEntity: ArrayList<DailyForecastEntity>? = null,
    @ColumnInfo(name = "information") val information: String,
    @ColumnInfo(name = "korea_forecast_map_img_url") val koreaForecastMapImgUrl: String,
    @Embedded val koreaModelGif: KoreaForecastModelGifEntity
)

fun AirDataEntity.mapToExternalModel(): AirData =
    AirData(
        station = station,
        date = date,
        airLevel = airLevel,
        detailAirData = detailAirDataEntity.mapToExternalModel(),
        dailyForecast = dailyForecastListEntity?.map { it.mapToExternalModel() } ?: listOf(),
        information = information,
        koreaForecastMapImgUrl = koreaForecastMapImgUrl
    )

@Serializable
data class DailyForecastEntity(
    val date: String,
    val airLevel: AirLevel,
)

fun DailyForecastEntity.mapToExternalModel(): DailyForecast =
    DailyForecast(
        date = date,
        airLevel = airLevel
    )

@Entity
data class DetailAirDataEntity(
    val pm25Level: AirLevel,
    val pm25Value: String,
    val pm10Level: AirLevel,
    val pm10Value: String,
    val no2Level: AirLevel,
    val no2Value: String,
    val so2Level: AirLevel,
    val so2Value: String,
    val coLevel: AirLevel,
    val coValue: String,
    val o3Level: AirLevel,
    val o3Value: String
)

fun DetailAirDataEntity.mapToExternalModel(): DetailAirData =
    DetailAirData(
        pm25Level = pm25Level,
        pm25Value = pm25Value,
        pm10Level = pm10Level,
        pm10Value = pm10Value,
        no2Level = no2Level,
        no2Value = no2Value,
        so2Level = so2Level,
        so2Value = so2Value,
        coLevel = coLevel,
        coValue = coValue,
        o3Level = o3Level,
        o3Value = o3Value
    )

@Entity
data class KoreaForecastModelGifEntity(
    val pm10GifUrl: String,
    val pm25GifUrl: String
)