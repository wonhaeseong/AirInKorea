package com.phil.airinkorea.data.model

import com.phil.airinkorea.database.model.*
import com.phil.airinkorea.domain.model.AirLevel
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.model.UserLocation
import com.phil.airinkorea.network.model.NetworkAirData
import com.phil.airinkorea.network.model.NetworkDetailAirData
import com.phil.airinkorea.network.model.NetworkForecastItem

fun NetworkAirData.mapToAirDataEntity(station: String): AirDataEntity =
    AirDataEntity(
        station = station,
        date = detailAirData.dataTime,
        airLevel = detailAirData.airLevel.mapToAirLevel(),
        detailAirDataEntity = detailAirData.mapToDetailAirDataEntity(),
        dailyForecastListEntity = forecast.map { it.mapToDailyForecastEntity() } as ArrayList<DailyForecastEntity>,
        information = information,
        koreaForecastMapImgUrl = koreaForecastMapImgUrl,
        koreaModelGif = KoreaForecastModelGifEntity(
            pm10GifUrl,
            pm25GifUrl
        )
    )


private fun NetworkDetailAirData.mapToDetailAirDataEntity(): DetailAirDataEntity =
    DetailAirDataEntity(
        pm25Level = pm25Grade.mapToAirLevel(),
        pm25Value = pm25Value,
        pm10Level = pm10Grade.mapToAirLevel(),
        pm10Value = pm10Value,
        no2Level = no2Grade.mapToAirLevel(),
        no2Value = no2Value,
        so2Level = so2Grade.mapToAirLevel(),
        so2Value = so2Value,
        coLevel = coGrade.mapToAirLevel(),
        coValue = coValue,
        o3Level = o3Grade.mapToAirLevel(),
        o3Value = o3Value
    )

private fun NetworkForecastItem.mapToDailyForecastEntity(): DailyForecastEntity =
    DailyForecastEntity(
        date = date,
        airLevel = airLevel.mapToAirLevel()
    )


private fun String?.mapToAirLevel(): AirLevel =
    when (this) {
        AirLevel.Level1.value -> AirLevel.Level1
        AirLevel.Level2.value -> AirLevel.Level2
        AirLevel.Level3.value -> AirLevel.Level3
        AirLevel.Level4.value -> AirLevel.Level4
        AirLevel.Level5.value -> AirLevel.Level5
        AirLevel.Level6.value -> AirLevel.Level6
        else -> {
            AirLevel.LevelError
        }
    }


fun UserLocation.mapToUserLocationEntity() =
        UserLocationEntity(
            enDo = `do`,
            enSigungu = sigungu,
            enEupmyeondong = eupmyeondong,
            station = station,
            bookmark = if(bookmark) 1 else 0
        )

fun Location.mapToGPSLocationEntity() =
    GPSLocationEntity(
        enDo = `do`,
        enSigungu = sigungu,
        enEupmyeondong = eupmyeondong,
        station = station
    )


