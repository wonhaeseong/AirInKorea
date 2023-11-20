package com.phil.airinkorea.data.model

data class AirData(
    val userLocation: UserLocation?,
    val date: String?,
    val airLevel: AirLevel,
    val detailAirData: DetailAirData,
    val dailyForecast: List<DailyForecast>,
    val information: String?,
    val koreaForecastModelGif: KoreaForecastModelGif
)

/**
 * 공기질에 따라 6단계로 분류
 */
enum class AirLevel(val value: String) {
    Level1("Good"),
    Level2("Moderate"),
    Level3("Poor"),
    Level4("Unhealthy"),
    Level5("Very Unhealthy"),
    Level6("Hazardous"),
    LevelError("Error")
}

/**
 * 일별 오염 단계
 */
data class DailyForecast(
    val date: String?,
    val airLevel: AirLevel,
)

/**
 * 6가지 물질에 대한 오염 수치와 오염 단계
 */
data class DetailAirData(
    val pm25Level: AirLevel,
    val pm25Value: String?,
    val pm10Level: AirLevel,
    val pm10Value: String?,
    val no2Level: AirLevel,
    val no2Value: String?,
    val so2Level: AirLevel,
    val so2Value: String?,
    val coLevel: AirLevel,
    val coValue: String?,
    val o3Level: AirLevel,
    val o3Value: String?
)

data class KoreaForecastModelGif(
    val pm10GifUrl: String?,
    val pm25GifUrl: String?
)
