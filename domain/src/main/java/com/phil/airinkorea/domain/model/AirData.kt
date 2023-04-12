package com.phil.airinkorea.domain.model

/**
 * @param location 위치 정보
 * @param date 데이터를 서버에서 가져온 시간
 * @param information 현재 한국의 대기 오염 상태에 대한 원인 및 정보
 * @param koreaForecastMapImgUrl 한반도 대기 오염 예측 모델의 이미지 URL
 */
data class AirData(
    val location: String,
    val date: String,
    val airLevel: AirLevel,
    val airData : DetailAirData?,
    val dailyForecast: List<DailyForecast>?,
    val information: String?,
    val koreaForecastMapImgUrl: String?
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
    Level6("Hazardous")
}

/**
 * 일별 오염 단계
 */
data class DailyForecast(
    val daysOfTheWeek: String,
    val date: String,
    val airLevel: String
)

/**
 * 6가지 물질에 대한 오염 수치와 오염 단계
 */
data class DetailAirData(
    val pm25Level: String,
    val pm25Value: Int,
    val pm10Level: String,
    val pm10Value: Int,
    val no2Level: String,
    val no2Value: Int,
    val so2Level: String,
    val so2Value: Int,
    val coLevel: String,
    val coValue: Int,
    val o3Level: String,
    val o3Value: Int
)
