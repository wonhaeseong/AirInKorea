package com.phil.airinkorea.data.model

import kotlinx.serialization.Serializable

data class AirData(
    val location: String,
    val date: String,
    val airLevel: AirLevel,
    val airData : DetailAirData?,
    val dailyForecast: List<DailyForecast>?,
    val information: String?,
    val koreaForecastMapImgUrl: String?
)

enum class AirLevel(val value: String) {
    Level1("Good"),
    Level2("Moderate"),
    Level3("Poor"),
    Level4("Unhealthy"),
    Level5("Very Unhealthy"),
    Level6("Hazardous")
}

@Serializable
data class DailyForecast(
    val daysOfTheWeek: String,
    val date: String,
    val airLevel: AirLevel
)

@Serializable
data class DetailAirData(
    val pm25Level: AirLevel,
    val pm25Value: Int,
    val pm10Level: AirLevel,
    val pm10Value: Int,
    val no2Level: AirLevel,
    val no2Value: Int,
    val so2Level: AirLevel,
    val so2Value: Int,
    val coLevel: AirLevel,
    val coValue: Int,
    val o3Level: AirLevel,
    val o3Value: Int
)

@Serializable
data class KoreaForecastModelGif(
    val pm10GifUrl: String,
    val pm25GIfUrl: String
)
