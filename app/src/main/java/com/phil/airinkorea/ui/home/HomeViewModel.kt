package com.phil.airinkorea.ui.viewmodel

import androidx.compose.ui.graphics.Color
import com.phil.airinkorea.model.*

/**
 *  @param isLoading 데이터의 로딩상태
 *  @param userLocation 사용자가 선택한 위치 혹은 사용자의 현재 위치 중 읍면동의 이름
 *  @param dataTime 데이터가 호출된 시간이 아닌 미세먼지 API에서 얻은 데이터 시간. 위치마다 15분 30분 간격
 *  @param airLevel 대기 오염 수준
 *  @param detailData DetailData 섹션을 위한 데이터
 *  @param hourlyForecastData hourlyForecast 섹션을 위한 데이터
 *  @param dailyForecastData dailyForecast 섹션을 위한 데이터
 */
data class HomeUiState(
    val isLoading: Boolean = true,
    val location: String,
    val dataTime: String,
    val airLevel: String,
    val detailData: DetailData,
    val hourlyForecastData: List<HourlyForecastData>,
    val dailyForecastData: List<DailyForecastData>
)

data class DetailData(
    val pm25Level: String,
    val pm25Value: Int,
    val pm25Color: Color,
    val pm10Level: String,
    val pm10Value: Int,
    val pm10Color: Color,
    val no2Level: String,
    val no2Value: Int,
    val no2Color: Color,
    val so2Level: String,
    val so2Value: Int,
    val so2Color: Color,
    val coLevel: String,
    val coValue: Int,
    val coColor: Color,
    val o3Level: String,
    val o3Value: Int,
    val o3Color: Color
)

data class DailyForecastData(
    val daysOfTheWeek: String,
    val date: String,
    val airLevel: String,
    val airLevelColor: Color
)

data class HourlyForecastData(
    val time: String, val airLevel: String, val airLevelColor: Color
)

class HomeViewModel {

}