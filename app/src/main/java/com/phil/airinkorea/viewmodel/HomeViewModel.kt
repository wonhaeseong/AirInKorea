package com.phil.airinkorea.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.phil.airinkorea.combine
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.DailyForecast
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.SelectedLocation
import com.phil.airinkorea.data.model.UserLocation
import com.phil.airinkorea.data.repository.AirDataRepository
import com.phil.airinkorea.data.repository.LocationRepository
import com.phil.airinkorea.manager.LocationManager
import com.phil.airinkorea.manager.PermissionManager
import com.phil.airinkorea.manager.SettingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class HomeUiState(
    val userLocation: UserLocation? = null,
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
    val selectedLocation: SelectedLocation = SelectedLocation.GPS,
    val isRefreshing: Boolean = false,  //당겨서 새로고침 상태
    val isInitializing: Boolean = false,
    val requestLocationPermission: Boolean = false,
    val resolvableApiException: ResolvableApiException? = null,
    val isPageLoading: Boolean = false,
    val isPermissionEnable: Boolean = true,
    val isGPSOn: Boolean = true,
)

data class DrawerUiState(
    val gps: UserLocation? = null,
    val bookmark: UserLocation? = null,
    val userLocationList: List<UserLocation> = emptyList(),
    val selectedLocation: SelectedLocation = SelectedLocation.GPS
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val airDataRepository: AirDataRepository,
    private val locationManager: LocationManager,
    private val permissionManager: PermissionManager,
    private val settingManager: SettingManager
) : ViewModel() {
    private val _isInitializing = MutableStateFlow(true)
    private val _isPageLoading = MutableStateFlow(false)
    private val _isPermissionEnable = MutableStateFlow(true)
    private val _isRefreshing = MutableStateFlow(false)
    private val _isGPSOn = MutableStateFlow(true)
    private val _requestLocationPermission = MutableStateFlow(false)
    private val _resolvableApiException: MutableStateFlow<ResolvableApiException?> =
        MutableStateFlow(null)
    private val _selectedLocation: StateFlow<UserLocation?> =
        locationRepository.getSelectedLocationStream().flowOn(Dispatchers.IO).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val drawerUiState: StateFlow<DrawerUiState> = combine(
        locationRepository.getGPSLocationStream(),
        locationRepository.getBookmarkStream(),
        locationRepository.getCustomLocationListStream(),
        _selectedLocation
    ) { gpsLocation, bookmark, customLocationList, selectedLocation ->
        DrawerUiState(
            gps = gpsLocation,
            bookmark = bookmark,
            userLocationList = customLocationList,
            selectedLocation = mapToSelectedLocation(selectedLocation)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DrawerUiState()
    )

    val homeUiState: StateFlow<HomeUiState> = combine(
        airDataRepository.getAirDataStream(),
        _selectedLocation,
        _isRefreshing,
        _isInitializing,
        _isPageLoading,
        _isPermissionEnable,
        _isGPSOn,
        _resolvableApiException,
        _requestLocationPermission
    ) { airData, selectedLocation, isRefreshing, isInitializing, isPageLoading, isPermissionEnable, isGPSOn, resolvableApiException, requestLocationPermission ->
        HomeUiState(
            userLocation = airData.userLocation,
            dataTime = airData.date,
            airLevel = airData.airLevel,
            detailAirData = airData.detailAirData,
            information = airData.information,
            dailyForecast = airData.dailyForecast,
            forecastModelUrl = airData.koreaForecastModelGif,
            selectedLocation = mapToSelectedLocation(selectedLocation),
            isRefreshing = isRefreshing,
            isInitializing = isInitializing,
            isPageLoading = isPageLoading,
            isPermissionEnable = isPermissionEnable,
            isGPSOn = isGPSOn,
            resolvableApiException = resolvableApiException,
            requestLocationPermission = requestLocationPermission
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(isInitializing = true)
    )

    init {
        initState()
    }

    private suspend fun mapToSelectedLocation(userLocation: UserLocation?) =
        if (userLocation != null) {
            when {
                userLocation.isGPS -> SelectedLocation.GPS
                userLocation.isBookmark -> SelectedLocation.Bookmark
                else -> {
                    SelectedLocation.UserLocation(
                        locationRepository.getCustomLocationListStream().first()
                            .indexOf(userLocation)
                    )
                }
            }
        } else {
            SelectedLocation.GPS
        }

    private fun initState() {
        viewModelScope.launch {
            when (mapToSelectedLocation(_selectedLocation.value)) {
                SelectedLocation.GPS -> {
                    when {
                        !checkLocationPermission() -> Unit

                        !enableLocationSetting() -> Unit

                        else -> {
                            fetchGPSLocationByCoordinate()
                            _isInitializing.value = false
                        }
                    }
                }

                else -> {
                    airDataRepository.updateAirData(_selectedLocation.value!!.location.station)
                    _isInitializing.value = false
                }
            }
        }
    }

    fun onRefreshHomeScreen() {
        _isRefreshing.value = true
        viewModelScope.launch {
            when (mapToSelectedLocation(_selectedLocation.value)) {
                SelectedLocation.GPS -> {
                    when {
                        !checkLocationPermission() -> Unit

                        !enableLocationSetting() -> Unit

                        else -> {
                            fetchGPSLocationByCoordinate()
                            _isRefreshing.value = false
                        }
                    }
                }

                else -> {
                    airDataRepository.updateAirData(_selectedLocation.value!!.location.station)
                    _isRefreshing.value = false
                }
            }

        }
    }

    fun onDrawerLocationClick(userLocation: UserLocation?) {
        _isPageLoading.value = true
        viewModelScope.launch {
            locationRepository.selectLocation(
                newLocation = userLocation,
                oldLocation = _selectedLocation.value
            )
            when (mapToSelectedLocation(_selectedLocation.value)) {
                SelectedLocation.GPS -> {
                    when {
                        !checkLocationPermission() -> Unit

                        !enableLocationSetting() -> Unit

                        else -> {
                            fetchGPSLocationByCoordinate()
                            _isPageLoading.value = false
                        }
                    }
                }

                else -> {
                    airDataRepository.updateAirData(_selectedLocation.value!!.location.station)
                    _isPageLoading.value = false
                }
            }
        }
    }

    private suspend fun fetchGPSLocationByCoordinate() {
        val coordinate = locationManager.getCurrentCoordinate()
        coordinate?.let {
            locationRepository.fetchGPSLocationByCoordinate(
                it.latitude,
                it.longitude
            )
        }
    }

    private suspend fun enableLocationSetting(): Boolean {
        return try {
            settingManager.enableLocationSetting()
            _isGPSOn.value = true
            true
        } catch (e: Throwable) {
            Log.d("location", "error $e")
            if (e is ResolvableApiException) {
                _resolvableApiException.value = e
            }
            _isGPSOn.value = false
            false
        }
    }

    fun onLocationPermissionGranted() {
        _requestLocationPermission.value = false
        when {
            _isInitializing.value -> {
                initState()
            }

            _isRefreshing.value -> {
                onRefreshHomeScreen()
            }

            else -> {
                Log.e("onPermissionGranted", "Unexpected GPS Call")
            }
        }
    }

    fun onLocationPermissionDenied() {
        viewModelScope.launch {
            _requestLocationPermission.value = false
            when {
                _isInitializing.value -> {
                    _isInitializing.value = false
                }

                _isRefreshing.value -> {
                    _isRefreshing.value = false
                }

                else -> {
                    Log.e("onPermissionDenied", "Unexpected GPS Call")
                }
            }
        }
    }

    fun onLocationSettingGranted() {
        _resolvableApiException.value = null
        when {
            _isInitializing.value -> {
                initState()
            }

            _isRefreshing.value -> {
                onRefreshHomeScreen()
            }

            else -> {
                Log.e("onLocationSettingGranted", "Unexpected GPS Call")
            }
        }
    }

    fun onLocationSettingDenied() {
        viewModelScope.launch {
            _resolvableApiException.value = null
            when {
                _isInitializing.value -> {
                    _isInitializing.value = false
                }

                _isRefreshing.value -> {
                    _isRefreshing.value = false
                }

                else -> {
                    Log.e("onLocationSettingDenied", "Unexpected GPS Call")
                }
            }
        }
    }

    private fun checkLocationPermission(): Boolean {
        return if (permissionManager.checkLocationPermission()) {
            _isPermissionEnable.value = true
            true
        } else {
            _isPermissionEnable.value = false
            _requestLocationPermission.value = true
            false
        }
    }
}