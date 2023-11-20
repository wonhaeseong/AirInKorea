package com.phil.airinkorea

import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.DailyForecast
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.AirDataRepository
import com.phil.airinkorea.data.repository.LocationRepository
import com.phil.airinkorea.manager.Coordinate
import com.phil.airinkorea.manager.LocationManager
import com.phil.airinkorea.manager.PermissionManager
import com.phil.airinkorea.manager.SettingManager
import com.phil.airinkorea.viewmodel.HomeUiState
import com.phil.airinkorea.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @Mock
    private lateinit var locationRepository: LocationRepository

    @Mock
    private lateinit var appStatusRepository: AppStatusRepository

    @Mock
    private lateinit var airDataRepository: AirDataRepository

    @Mock
    private lateinit var locationManager: LocationManager

    @Mock
    private lateinit var permissionManager: PermissionManager

    @Mock
    private lateinit var settingManager: SettingManager

    private val successLocation =
        Location(`do` = "a", sigungu = "b", eupmyeondong = "c", station = "d")
    private val successAirData = AirData(
        station = "d",
        date = "Saturday 12/25/2022 9:10 PM ",
        airLevel = AirLevel.Level1,
        detailAirData =
        DetailAirData(
            pm25Level = AirLevel.Level5,
            pm25Value = "25",
            pm10Level = AirLevel.Level4,
            pm10Value = "69",
            no2Level = AirLevel.Level2,
            no2Value = "0.011",
            so2Level = AirLevel.Level2,
            so2Value = "0.003",
            coLevel = AirLevel.Level5,
            coValue = "0.5",
            o3Level = AirLevel.Level1,
            o3Value = "0.049"
        ),
        dailyForecast = listOf(
            DailyForecast(
                date = "2023-04-28",
                airLevel = AirLevel.Level1
            ),
            DailyForecast(
                date = "2023-04-29",
                airLevel = AirLevel.Level1
            ),
            DailyForecast(
                date = "2023-04-30",
                airLevel = AirLevel.Level1
            ),
            DailyForecast(
                date = "2023-05-01",
                airLevel = AirLevel.Level5
            ),
            DailyForecast(
                date = "2023-05-02",
                airLevel = AirLevel.Level1
            ),
            DailyForecast(
                date = "2023-05-03",
                airLevel = AirLevel.Level1
            ),
            DailyForecast(
                date = "2023-05-04",
                airLevel = AirLevel.Level1
            )
        ),
        information =
        "The air quality will be generally 'average' due to the smooth air diffusion and precipitation, but the concentration is expected to be somewhat high in most western areas due to the inflow of foreign fine dust at night.",
        koreaForecastModelGif = KoreaForecastModelGif(null, null)
    )

    private val failAirData = AirData(
        station = null,
        date = null,
        airLevel = AirLevel.LevelError,
        detailAirData = DetailAirData(
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
        dailyForecast = arrayListOf(),
        information = null,
        koreaForecastModelGif = KoreaForecastModelGif(
            pm10GifUrl = null,
            pm25GifUrl = null
        )
    )


    @Before
    fun setUp() {

    }

    @After
    fun finish() {

    }

    //설정_실행_기대
    //https://beomseok95.tistory.com/297
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `초기화시점 0페이지,위치권한 및 GPS 켜짐 상태일 때,airLevel != LevelError`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        //given
        whenever(appStatusRepository.getDefaultPage().first()).thenReturn(0)
        whenever(permissionManager.checkLocationPermission()).thenReturn(true)
        doNothing().whenever(settingManager.enableLocationSetting())
        whenever(locationManager.getCurrentCoordinate()).thenReturn(
            Coordinate(1.0, 2.0)
        )
        doNothing().whenever(
            locationRepository.fetchGPSLocationByCoordinate(
                1.0, 2.0
            )
        )
        whenever(locationRepository.getGPSLocation().first()).thenReturn(
            successLocation
        )
        whenever(airDataRepository.getAirData("d")).thenReturn(
            successAirData
        )
        val homeViewModel = HomeViewModel(
            locationRepository = locationRepository,
            appStatusRepository = appStatusRepository,
            airDataRepository = airDataRepository,
            locationManager = locationManager,
            permissionManager = permissionManager,
            settingManager = settingManager
        )
        //when,then
        val result = homeViewModel.homeUiState.first()
        val expect = HomeUiState(
            location = successLocation,
            dataTime = successAirData.date,
            airLevel = successAirData.airLevel,
            detailAirData = successAirData.detailAirData,
            information = successAirData.information,
            dailyForecast = successAirData.dailyForecast,
            forecastModelUrl = successAirData.koreaForecastModelGif,
            page = 0,
            isRefreshing = false,
            isInitializing = false,
            requestLocationPermission = false,
            resolvableApiException = null,
            isPageLoading = false,
            isPermissionEnable = true,
            isGPSOn = true
        )
        assertEquals(expect, result)
    }

}