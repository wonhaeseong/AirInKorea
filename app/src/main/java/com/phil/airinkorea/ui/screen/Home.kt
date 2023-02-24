package com.phil.airinkorea.ui.screen

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.model.DailyForecastData
import com.phil.airinkorea.model.DetailData
import com.phil.airinkorea.model.HourlyForecastData
import com.phil.airinkorea.ui.composables.CollapsingToolbar
import com.phil.airinkorea.ui.composables.DailyForecast
import com.phil.airinkorea.ui.composables.Detail
import com.phil.airinkorea.ui.composables.HourlyForecast
import com.phil.airinkorea.ui.theme.*
import com.phil.airinkorea.ui.toolbarmanagement.CollapsingState
import com.phil.airinkorea.ui.toolbarmanagement.ToolbarState

private val MaxToolbarHeight = 150.dp
private val MinToolbarHeight = 50.dp

@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return rememberSaveable(saver = CollapsingState.Saver) {
        CollapsingState(toolbarHeightRange)
    }
}

@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    val toolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberToolbarState(toolbarHeightRange = toolbarHeightRange)
    val scrollState = rememberScrollState()
    println(
        "height:${with(LocalDensity.current) { toolbarState.height.toDp() }},scrollValue:${
            with(
                LocalDensity.current
            ) { toolbarState.scrollValue.toDp() }
        },progress:${toolbarState.progress}"
    )
    toolbarState.scrollValue = scrollState.value
    AIKTheme(pollutionLevel = PollutionLevel.EXCELLENT) {
        Column(
            modifier = Modifier
                .background(AIKTheme.colors.core_background)
                .statusBarsPadding()
        ) {
            CollapsingToolbar(
                progress = toolbarState.progress,
                onMenuButtonClicked = { /*TODO*/ },
                location = "HJHHH",
                date = "213123",
                airLevel = "GOOD",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(LocalDensity.current) { toolbarState.height.toDp() })
            )
            Box(modifier = Modifier.verticalScroll(scrollState)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
//                    .verticalScroll(scrollState, flingBehavior = customFlingBehavior)
                ) {
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
                        modifier = Modifier.padding(10.dp)
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
