package com.phil.airinkorea.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.DailyForecast
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.AirDataRepository
import com.phil.airinkorea.data.repository.AppStatusRepository
import com.phil.airinkorea.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface HomeUiState {
    object Initializing : HomeUiState //앱 초기 로딩 화면
    data class Success(
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
        var page: Int = 0,
        var isRefreshing: Boolean = false,  //당겨서 새로고침 상태
        var isPageLoading: Boolean = false  //Page 변경시 초기 로딩 상태
    ) : HomeUiState
}

sealed interface DrawerUiState {
    object Loading : DrawerUiState
    data class Success(
        val gps: Location? = null,
        val bookmark: Location? = null,
        val userLocationList: List<Location> = emptyList(),
        val page: Int = 0
    ) : DrawerUiState
}

sealed interface ActivityEvent {
    object GetGPSLocation : ActivityEvent
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val appStatusRepository: AppStatusRepository,
    private val airDataRepository: AirDataRepository
) : ViewModel() {

    val homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Initializing)
    private val locationStateFlow: MutableStateFlow<Location?> = MutableStateFlow(null)

    private val _activityEvent = MutableSharedFlow<ActivityEvent>()
    val activityEvent: SharedFlow<ActivityEvent> = _activityEvent.asSharedFlow()

    //page가 변경되면 현재 location이 변경되고 그에 맞는
    private val pageStateFlow: StateFlow<Int> = appStatusRepository.getDefaultPage().onEach {
        when (it) {
            0 -> {
                getGPSLocation()
            }

            else -> {
                val location =
                    if (it == 1) {
                        locationRepository.getBookmark().first()
                    } else {
                        locationRepository.getUserLocationList().first()[it - 2]
                    }
                val airData = getAirData(location.station)
                emitHomeUiState(
                    HomeUiState.Success(
                        location = location,
                        dataTime = airData.date,
                        airLevel = airData.airLevel,
                        detailAirData = airData.detailAirData,
                        information = airData.information,
                        dailyForecast = airData.dailyForecast,
                        forecastModelUrl = airData.koreaForecastModelGif,
                        page = it,
                        isPageLoading = false
                    )
                )
                locationStateFlow.emit(location)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0
    )

    val drawerUiState: StateFlow<DrawerUiState> = combine(
        locationRepository.getGPSLocation(),
        locationRepository.getBookmark(),
        locationRepository.getUserLocationList(),
        appStatusRepository.getDefaultPage()
    ) { gpsLocation, bookmark, userLocationList, page ->
        DrawerUiState.Success(
            gps = gpsLocation,
            bookmark = bookmark,
            userLocationList = userLocationList,
            page = page
        )
    }.onStart {
        DrawerUiState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DrawerUiState.Loading
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG", "init")
            emitHomeUiState(HomeUiState.Initializing)
            pageStateFlow.collect {
                Log.d("TAG", it.toString())
            }
        }
    }

    fun onRefreshHomeScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentHomeUiState = homeUiState.value
            if (currentHomeUiState is HomeUiState.Success) {
                homeUiState.update {
                    currentHomeUiState.copy(isRefreshing = true)
                }
                val page = pageStateFlow.value
                if (page == 0) {
                    getGPSLocation()
                } else {
                    val location = locationStateFlow.value
                    val airData = getAirData(location?.station)
                    emitHomeUiState(
                        HomeUiState.Success(
                            location = location,
                            dataTime = airData.date,
                            airLevel = airData.airLevel,
                            detailAirData = airData.detailAirData,
                            information = airData.information,
                            dailyForecast = airData.dailyForecast,
                            forecastModelUrl = airData.koreaForecastModelGif,
                            page = page,
                            isRefreshing = false,
                            isPageLoading = false
                        )
                    )
                }
            }
        }
    }

    fun onDrawerLocationClick(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentHomeUiState = homeUiState.value
            if (currentHomeUiState is HomeUiState.Success) {
                homeUiState.update {
                    currentHomeUiState.copy(isPageLoading = true)
                }
            }
            appStatusRepository.fetchDefaultPage(page)
        }
    }

    private suspend fun getGPSLocation() {
        _activityEvent.emit(ActivityEvent.GetGPSLocation)
    }

    suspend fun getAirData(station: String?): AirData {
        withContext(Dispatchers.Default) {
            if (station != null) {
                airDataRepository.fetchAirData(station)
            }
        }
        Log.d("TAG", station.toString())
        return airDataRepository.getAirData(station).first()
            .also { Log.d("TAG", it.toString()) }
    }

    suspend fun emitHomeUiState(newHomeUiState: HomeUiState) {
        homeUiState.emit(newHomeUiState)
    }

    fun fetchGPSLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                locationRepository.fetchGPSLocation(
                    latitude,
                    longitude
                )
            }
            val newGPSLocation = locationRepository.getGPSLocation().first()
            val airData =
                withContext(Dispatchers.IO) {
                    getAirData(newGPSLocation?.station)
                }
            Log.d("TAG1", airData.toString())
            emitHomeUiState(
                HomeUiState.Success(
                    location = newGPSLocation,
                    dataTime = airData.date,
                    airLevel = airData.airLevel,
                    detailAirData = airData.detailAirData,
                    information = airData.information,
                    dailyForecast = airData.dailyForecast,
                    forecastModelUrl = airData.koreaForecastModelGif,
                    page = 0,
                    isRefreshing = false,
                    isPageLoading = false
                )
            )
        }
    }

    fun fetchToPageBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            appStatusRepository.fetchDefaultPage(1)
        }
    }

    fun setRefreshingState(isRefreshing: Boolean) {
        val currentHomeUiState = homeUiState.value
        if (currentHomeUiState is HomeUiState.Success) {
            homeUiState.update {
                currentHomeUiState.copy(isRefreshing = isRefreshing)
            }
        }
    }
}