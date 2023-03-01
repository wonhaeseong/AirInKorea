package com.phil.airinkorea.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.phil.airinkorea.model.DailyForecastData
import com.phil.airinkorea.model.DetailData
import com.phil.airinkorea.model.HourlyForecastData
import com.phil.airinkorea.ui.composables.*
import com.phil.airinkorea.ui.composables.home.Detail
import com.phil.airinkorea.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = scope.launch {
        refreshing = true
        delay(3000)
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    AIKTheme(pollutionLevel = PollutionLevel.EXCELLENT) {
        Box(
            modifier = modifier
                .background(AIKTheme.colors.core_background)
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = Color.Transparent,
                topBar = {
                    AIKTopAppBar(
                        location = "ddd",
                        onMenuButtonClicked = { scope.launch { scaffoldState.drawerState.open() } })
                },
                drawerContent = { Drawer() },
                drawerGesturesEnabled = true,
                drawerScrimColor = scrimColor,
                modifier = Modifier
                    .statusBarsPadding()
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .pullRefresh(state)
                        .verticalScroll(scrollState)
                ) {
                    DateInfo(date = "Saturday, January 21, 2023 9:10 PM")
                    Spacer(modifier = Modifier.size(35.dp))
                    AirValue(airLevel = "Fine")
                    Spacer(modifier = Modifier.size(10.dp))
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth()
                            .height(
                                if (refreshing) {
                                    140.dp
                                } else {
                                    with(LocalDensity.current) {
                                        lerp(0.dp, 140.dp, state.progress.coerceIn(0f..1f))
                                    }
                                }
                            )
                    ) {
                        CloudIndicator(isPlaying = refreshing)
                    }
                    Detail(
                        detailData = DetailData(
                            pm25Level = "Dangerous",
                            pm25Value = 10,
                            pm25Color = level1_core,
                            pm10Level = "Dangerous",
                            pm10Value = 10,
                            pm10Color = level2_core,
                            no2Level = "Dangerous",
                            no2Value = 10,
                            no2Color = level3_core,
                            so2Level = "Dangerous",
                            so2Value = 10,
                            so2Color = level4_core,
                            coLevel = "Dangerous",
                            coValue = 10,
                            coColor = level5_core,
                            o3Level = "Dangerous",
                            o3Value = 10,
                            o3Color = level6_core
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                    HourlyForecast(
                        hourlyForecastDataList = listOf(
                            HourlyForecastData(
                                time = "10AM",
                                airLevel = "Excellent",
                                airLevelColor = level1_core
                            ),
                            HourlyForecastData(
                                time = "11AM",
                                airLevel = "Good",
                                airLevelColor = level2_core
                            ),
                            HourlyForecastData(
                                time = "12PM",
                                airLevel = "Fine",
                                airLevelColor = level3_core
                            ),
                            HourlyForecastData(
                                time = "1PM",
                                airLevel = "Moderate",
                                airLevelColor = level4_core
                            ),
                            HourlyForecastData(
                                time = "2PM",
                                airLevel = "Poor",
                                airLevelColor = level5_core
                            ),
                            HourlyForecastData(
                                time = "3PM",
                                airLevel = "Bad",
                                airLevelColor = level6_core
                            ),
                            HourlyForecastData(
                                time = "4PM",
                                airLevel = "Unhealthy",
                                airLevelColor = level7_core
                            ),
                            HourlyForecastData(
                                time = "5PM",
                                airLevel = "Dangerous",
                                airLevelColor = level8_core
                            )
                        ),
                        contentPaddingValues = PaddingValues(horizontal = 10.dp)
                    )
                    DailyForecast(
                        dailyForecastDataList = listOf(
                            DailyForecastData("Sunday", "12/16/2022", "Excellent", level1_core),
                            DailyForecastData("Monday", "12/17/2022", "Good", level2_core),
                            DailyForecastData("Tuesday", "12/18/2022", "Fine", level3_core),
                            DailyForecastData("Wednesday", "12/19/2022", "Moderate", level4_core),
                            DailyForecastData("Thursday", "12/20/2022", "Poor", level5_core),
                            DailyForecastData("Friday", "12/21/2022", "Bad", level6_core),
                            DailyForecastData("Saturday", "12/22/2022", "Unhealthy", level7_core)
                        ), modifier = Modifier.padding(10.dp)
                    )
//                    Map(modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}
