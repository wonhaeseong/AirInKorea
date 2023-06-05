package com.phil.airinkorea.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.phil.airinkorea.domain.model.AirLevel
import com.phil.airinkorea.domain.model.DailyForecast
import com.phil.airinkorea.domain.model.DetailAirData
import com.phil.airinkorea.domain.model.KoreaForecastModelGif
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.repository.LocationRepository
import com.phil.airinkorea.domain.usecases.airdata.GetAirDataUseCase
import com.phil.airinkorea.domain.usecases.bookmark.GetBookmarkUseCase
import com.phil.airinkorea.domain.usecases.gps.GetGPSLocationUseCase
import com.phil.airinkorea.domain.usecases.user.FetchDefaultPageUseCase
import com.phil.airinkorea.domain.usecases.user.GetDefaultPageUseCase
import com.phil.airinkorea.domain.usecases.user.GetUserLocationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
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
    var page: Int = 0
)

data class DrawerUiState(
    val gps: Location? = null,
    val bookmark: Location? = null,
    val userLocationList: List<Location> = emptyList(),
    val page: Int = 0
)

//냉정하게 사용자가 실행하는 것만 usecase로 남기고 나머지는 repository로 넘기기
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAirDataUseCase: GetAirDataUseCase,//일반
    getDefaultPageUseCase: GetDefaultPageUseCase,//일반
    getBookmarkUseCase: GetBookmarkUseCase,//flow
    private val fetchDefaultPageUseCase: FetchDefaultPageUseCase,
    getGPSLocationUseCase: GetGPSLocationUseCase,
    getUserLocationListUseCase: GetUserLocationListUseCase,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _homeUiState =
        MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    val drawerUiState = combine(
        getGPSLocationUseCase(),
        getBookmarkUseCase(),
        getUserLocationListUseCase()
    ) { gps, bookmark, userLocationList ->
        DrawerUiState(
            gps = gps,
            bookmark = bookmark,
            userLocationList = userLocationList
        )
    }.onEach { drawerUiState ->
        when (val pageNum = _homeUiState.value.page) {
            0 -> {
                Log.d("TAG1", "GPS입니다.")
                _homeUiState.update {
                    it.copy(
                        location = drawerUiState.gps,
                    )
                }
                getAirData()
            }

            1 -> {
                Log.d("TAG1", "bookmark입니다.")
                _homeUiState.update {
                    it.copy(
                        location = drawerUiState.bookmark,
                    )
                }
                getAirData()

            }

            else -> {
                Log.d("TAG1", "normal입니다.")
                _homeUiState.update {
                    it.copy(
                        location = drawerUiState.userLocationList[pageNum - 2],
                    )
                }
                getAirData()
            }
        }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DrawerUiState())


        getDefaultPageUseCase()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            page.collect { pageNum ->
                when (pageNum) {
                    0 -> {
                        Log.d("TAG PAGE", "GPS입니다.")
                        _homeUiState.update {
                            it.copy(
                                location = drawerUiState.value.gps,
                                page = pageNum
                            )
                        }
                        getAirData()
                    }

                    1 -> {
                        Log.d("TAG PAGE", "bookmark입니다.")
                        _homeUiState.update {
                            it.copy(
                                location = drawerUiState.value.bookmark,
                                page = pageNum
                            )
                        }
                        getAirData()
                    }

                    else -> {
                        Log.d("TAG PAGE", "normal입니다.")
                        _homeUiState.update {
                            it.copy(
                                location = drawerUiState.value.userLocationList[pageNum - 2],
                                page = pageNum
                            )
                        }
                        getAirData()
                    }
                }
            }
        }
    }

    fun fetchAirData(
//        requestLocationPermissionCallback: () -> Unit,
        requestTurnOnGPS: (ResolvableApiException) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG fetchAirData", "실행")
            _homeUiState.update { it.copy(isRefreshing = true) }
            if (page.value == 0) {
                updateGPS(
//                    requestLocationPermissionCallback = requestLocationPermissionCallback,
                    requestTurnOnGPS = requestTurnOnGPS,
                )
            } else {
                getAirData()
            }
        }
    }

    private fun updateGPS(
//        requestLocationPermissionCallback: () -> Unit,
        requestTurnOnGPS: (ResolvableApiException) -> Unit,
    ) {
//        requestLocationPermissionCallback()
        when (val exception = locationRepository.checkGPSOn()) {
            null -> {
                Log.d("TAG10", "dd")
                locationRepository.fetchGPSLocation { getAirData() }
            }

            else -> {
                requestTurnOnGPS(exception)
                Log.d("TAG10", "aa")
            }
        }
    }

    fun fetchPageAndLocation(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG5", page.toString())
            fetchDefaultPageUseCase(page)
        }
    }

    fun getAirData() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG airdata", _homeUiState.value.toString())
            val location = _homeUiState.value.location
            if (location != null) {
                val airData = getAirDataUseCase(location.station)
                _homeUiState.update { uiState ->
                    uiState.copy(
                        dataTime = airData.date,
                        airLevel = airData.airLevel,
                        detailAirData = airData.detailAirData,
                        dailyForecast = airData.dailyForecast,
                        information = airData.information,
                        forecastModelUrl = airData.koreaForecastModelGif,
                        isRefreshing = false,
                        isInitLoaded = true
                    )
                }
            } else {
                _homeUiState.update {
                    HomeUiState().copy(
                        isRefreshing = false
                    )
                }

            }
        }
    }
}