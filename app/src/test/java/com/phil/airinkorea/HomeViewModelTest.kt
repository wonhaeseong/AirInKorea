package com.phil.airinkorea

import app.cash.turbine.test
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.DailyForecast
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.Page
import com.phil.airinkorea.data.repository.AirDataRepository
import com.phil.airinkorea.data.repository.LocationRepository
import com.phil.airinkorea.manager.PermissionManager
import com.phil.airinkorea.manager.SettingManager
import com.phil.airinkorea.viewmodel.HomeViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var locationRepository: LocationRepository
    private lateinit var airDataRepository: AirDataRepository
    private lateinit var permissionManager: PermissionManager
    private lateinit var settingManager: SettingManager
    private lateinit var homeViewModel: HomeViewModel

    private val successLocation =
        Location(`do` = "a", sigungu = "b", eupmyeondong = "c", station = "d")
    private val successAirData = AirData(
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

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        locationRepository = mockk()
        airDataRepository = mockk()
        permissionManager = mockk()
        settingManager = mockk()
        every { airDataRepository.getAirDataStream() } returns flowOf(failAirData)
        every { locationRepository.getSelectedLocationStream() } returns flowOf(null)
        every { locationRepository.getGPSLocationStream() } returns flowOf(null)
        every { locationRepository.getBookmarkStream() } returns flowOf(
            Location(
                `do` = "Seoul",
                sigungu = "Yongsan-gu",
                eupmyeondong = "Namyeong-dong",
                station = "한강대로"
            )
        )
        every { locationRepository.getCustomLocationListStream() } returns flowOf(emptyList())
        every { permissionManager.checkLocationPermission() } returns true
    }

    @Test
    fun homeUiState_whenInitialized_thenShowInitializing() {
        every { locationRepository.getCurrentPageStream() } returns flowOf(Page.GPS)
        every { permissionManager.checkLocationPermission() } returns true
        homeViewModel =
            HomeViewModel(locationRepository, airDataRepository, permissionManager, settingManager)
        assertEquals(true, homeViewModel.homeUiState.value.isInitializing)
    }

    @Test
    fun `onRefreshHomeScreen should update airdata on GPSPage, when GPS, Permission On `() =
        runTest {
            //given
            every { locationRepository.getCurrentPageStream() } returns flowOf(Page.GPS)
            every { permissionManager.checkLocationPermission() } returns true
            coEvery { settingManager.enableLocationSetting() } just Runs
            coEvery { airDataRepository.updateAirData(Page.GPS) } coAnswers { delay(500) }
            homeViewModel =
                HomeViewModel(
                    locationRepository,
                    airDataRepository,
                    permissionManager,
                    settingManager
                )
            //when
            homeViewModel.onRefreshHomeScreen()
            //then
            homeViewModel.homeUiState.test {
                assertEquals(false, awaitItem().isRefreshing)
                assertEquals(true, awaitItem().isRefreshing)
                assertEquals(false, awaitItem().isRefreshing)
            }
            coVerify {
                airDataRepository.updateAirData(Page.GPS)
            }
        }

    @Test
    fun `onRefreshHomeScreen shouldn't update airdata on GPSPage, when GPS on Permission off`() =
        runTest {
            //given
            every { locationRepository.getCurrentPageStream() } returns flowOf(Page.GPS)
            every { permissionManager.checkLocationPermission() } returns false
            coEvery { settingManager.enableLocationSetting() } just Runs
            coEvery { airDataRepository.updateAirData(Page.GPS) } coAnswers { delay(500) }
            homeViewModel =
                HomeViewModel(
                    locationRepository,
                    airDataRepository,
                    permissionManager,
                    settingManager
                )
            //when
            homeViewModel.onRefreshHomeScreen()
            advanceUntilIdle()
            //then
            coVerify(exactly = 0) {
                airDataRepository.updateAirData(Page.GPS)
            }
        }

    @Test
    fun `onRefreshHomeScreen shouldn't update airdata on GPSPage, when GPS off Permission on`() =
        runTest {
            val resolvableApiException = ResolvableApiException(Status.RESULT_CANCELED)
            //given
            every { locationRepository.getCurrentPageStream() } returns flowOf(Page.GPS)
            every { permissionManager.checkLocationPermission() } returns true
            coEvery { settingManager.enableLocationSetting() } throws resolvableApiException
            coEvery { airDataRepository.updateAirData(Page.GPS) } coAnswers { delay(500) }
            homeViewModel =
                HomeViewModel(
                    locationRepository,
                    airDataRepository,
                    permissionManager,
                    settingManager
                )
            //when
            homeViewModel.onRefreshHomeScreen()
            advanceUntilIdle()

            //then
            coVerify(exactly = 0) {
                airDataRepository.updateAirData(Page.GPS)
            }
        }


    @Test
    fun `onRefreshHomeScreen shouldn't update airdata on GPSPage, when GPS,Permission off`() =
        runTest {
            val resolvableApiException = ResolvableApiException(Status.RESULT_CANCELED)
            //given
            every { locationRepository.getCurrentPageStream() } returns flowOf(Page.GPS)
            every { permissionManager.checkLocationPermission() } returns false
            coEvery { settingManager.enableLocationSetting() } throws resolvableApiException
            coEvery { airDataRepository.updateAirData(Page.GPS) } coAnswers { delay(500) }
            homeViewModel =
                HomeViewModel(
                    locationRepository,
                    airDataRepository,
                    permissionManager,
                    settingManager
                )

            //when
            homeViewModel.onRefreshHomeScreen()
            advanceUntilIdle()

            //then
            coVerify(exactly = 0) {
                airDataRepository.updateAirData(Page.GPS)
            }
        }

    @Test
    fun `onRefreshHomeScreen should update airdata on BookmarkPage`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        //given
        every { locationRepository.getCurrentPageStream() } returns flowOf(Page.Bookmark)
        coEvery { airDataRepository.updateAirData(Page.Bookmark) } coAnswers { delay(500) }

        homeViewModel =
            HomeViewModel(
                locationRepository,
                airDataRepository,
                permissionManager,
                settingManager
            )

        //when
        homeViewModel.homeUiState.test {
            assertEquals(Page.GPS, awaitItem().page)
            assertEquals(
                Page.Bookmark,
                awaitItem().page
            )
        }
        homeViewModel.onRefreshHomeScreen()
        advanceUntilIdle()
        //then
        coVerify {
            airDataRepository.updateAirData(Page.Bookmark)
        }
    }

    @Test
    fun `onRefreshHomeScreen should update airdata on CustomPage`() = runTest {
        //given
        val expectPage = Page.CustomLocation(0)
        every { locationRepository.getCurrentPageStream() } returns flowOf(expectPage)
        coEvery { airDataRepository.updateAirData(expectPage) } coAnswers { delay(500) }
        homeViewModel =
            HomeViewModel(
                locationRepository,
                airDataRepository,
                permissionManager,
                settingManager
            )
        //when
        homeViewModel.homeUiState.test {
            assertEquals(Page.GPS, awaitItem().page)
            assertEquals(
                expectPage,
                awaitItem().page
            )
        }
        homeViewModel.onRefreshHomeScreen()
        advanceUntilIdle()
        //then
        coVerify {
            airDataRepository.updateAirData(expectPage)
        }
    }
}