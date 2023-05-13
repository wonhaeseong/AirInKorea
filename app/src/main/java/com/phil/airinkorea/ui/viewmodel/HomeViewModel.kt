package com.phil.airinkorea.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.domain.model.AirLevel
import com.phil.airinkorea.domain.model.DailyForecast
import com.phil.airinkorea.domain.model.DetailAirData
import com.phil.airinkorea.domain.model.KoreaForecastModelGif
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.model.Mode
import com.phil.airinkorea.domain.usecases.airdata.FetchAirDataUseCase
import com.phil.airinkorea.domain.usecases.airdata.GetAirDataUseCase
import com.phil.airinkorea.domain.usecases.user.FetchDefaultModeUseCase
import com.phil.airinkorea.domain.usecases.user.GetDefaultModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val location: Location? = null,
    val dataTime: String? = null,
    val airLevel: AirLevel = AirLevel.LevelError,
    val detailAirData: DetailAirData = DetailAirData(
        pm25Level = AirLevel.LevelError,
        pm25Value = null,
        pm10Level = AirLevel.LevelError,
        pm10Value = null,
        no2Level = AirLevel.LevelError,
        no2Value = null,
        so2Level = AirLevel.LevelError,
        so2Value = null,
        coLevel = AirLevel.LevelError,
        coValue = null,
        o3Level = AirLevel.LevelError,
        o3Value = null
    ),
    val information: String? = null,
    val dailyForecast: List<DailyForecast> = listOf(
        DailyForecast(
            date = null,
            airLevel = AirLevel.LevelError
        ),
        DailyForecast(
            date = null,
            airLevel = AirLevel.LevelError
        ),
        DailyForecast(
            date = null,
            airLevel = AirLevel.LevelError
        ),
        DailyForecast(
            date = null,
            airLevel = AirLevel.LevelError
        ),
        DailyForecast(
            date = null,
            airLevel = AirLevel.LevelError
        ),
        DailyForecast(
            date = null,
            airLevel = AirLevel.LevelError
        ),
        DailyForecast(
            date = null,
            airLevel = AirLevel.LevelError
        )
    ),
    val forecastModelUrl: KoreaForecastModelGif = KoreaForecastModelGif(
        pm10GifUrl = null,
        pm25GifUrl = null
    ),
    var isInitLoaded: Boolean = false,
    var isRefreshing: Boolean = false,
    var isGPS: Boolean = false,
    var gps: Location? = null,
    var bookmark: Location? = null,
    var userLocationList: List<Location> = emptyList()
)


@HiltViewModel
class HomeViewModel @Inject constructor(
    getDefaultModeUseCase: GetDefaultModeUseCase,
    getAirDataUseCase: GetAirDataUseCase,
    private val fetchDefaultModeUseCase: FetchDefaultModeUseCase,
    private val fetchAirDataUseCase: FetchAirDataUseCase
) : ViewModel() {
    private val _airData = MutableStateFlow(HomeUiState())
    val airData: StateFlow<HomeUiState> = _airData
    private var location: Location = Location("a", "b", "c", "3공단")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAirDataUseCase(location.station)
            getDefaultModeUseCase().combine(
                getAirDataUseCase(station = location.station)
            ) { isGPSMode, airData ->
                HomeUiState(
                    location = location,
                    dataTime = airData.date,
                    airLevel = airData.airLevel,
                    detailAirData = airData.detailAirData,
                    dailyForecast = airData.dailyForecast,
                    information = airData.information,
                    forecastModelUrl = airData.koreaForecastModelGif,
                    isInitLoaded = true,
                    isGPS = isGPSMode == Mode.GPS
                )
            }.collect {
                _airData.value = it
                Log.d("TAG HomeViewModel", it.toString())
            }
        }
    }

    fun fetchAirData() {
        viewModelScope.launch(Dispatchers.IO) {
            _airData.update { it.copy(isRefreshing = true) }
            fetchAirDataUseCase(location.station)
            _airData.update { it.copy(isRefreshing = false) }
        }
    }

    fun fetchDefaultMode(mode: Mode) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchDefaultModeUseCase(mode = mode)
        }
    }
}