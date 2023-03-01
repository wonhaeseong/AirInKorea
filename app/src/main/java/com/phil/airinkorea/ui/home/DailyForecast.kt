package com.phil.airinkorea.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.model.DailyForecastData
import com.phil.airinkorea.ui.commoncomponents.ExpendableTitleBar
import com.phil.airinkorea.ui.theme.*

@Composable
fun DailyForecast(
    modifier: Modifier = Modifier,
    dailyForecastDataList: List<DailyForecastData>
) {
    var expandedState by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ExpendableTitleBar(
            expandedState = expandedState,
            titleText = "Daily Forecast",
            onClick = { expandedState = !expandedState })
        Spacer(modifier = Modifier.size(8.dp))
        DailyForecastList(
            expandedState = expandedState,
            dailyForecastDataList = dailyForecastDataList,
            onClick = { expandedState = !expandedState }
        )
    }
}


@Composable
fun DailyForecastList(
    modifier: Modifier = Modifier,
    expandedState: Boolean,
    dailyForecastDataList: List<DailyForecastData>,
    onClick: () -> Unit
) {
    val backgroundColorList: List<Color> = listOf(
        AIKTheme.colors.core_container,
        Color.White,
        AIKTheme.colors.core_container,
        Color.White,
        AIKTheme.colors.core_container,
        Color.White,
        AIKTheme.colors.core_container
    )
    Surface(
        shape = Shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            for (i in 0..2) {
                DailyForecastComponent(
                    backgroundColor = backgroundColorList[i],
                    daysOfTheWeek = dailyForecastDataList[i].daysOfTheWeek,
                    date = dailyForecastDataList[i].date,
                    airLevel = dailyForecastDataList[i].airLevel,
                    airLevelColor = dailyForecastDataList[i].airLevelColor
                )
            }
            AnimatedVisibility(expandedState) {
                Column {
                    for (i in 3..6) {
                        DailyForecastComponent(
                            backgroundColor = backgroundColorList[i],
                            daysOfTheWeek = dailyForecastDataList[i].daysOfTheWeek,
                            date = dailyForecastDataList[i].date,
                            airLevel = dailyForecastDataList[i].airLevel,
                            airLevelColor = dailyForecastDataList[i].airLevelColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DailyForecastComponent(
    backgroundColor: Color,
    daysOfTheWeek: String,
    date: String,
    airLevel: String,
    airLevelColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(backgroundColor)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = daysOfTheWeek,
                color = AIKTheme.colors.on_core_container,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = date,
                color = AIKTheme.colors.on_core_container_subtext,
                style = MaterialTheme.typography.body2
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
        ) {
            Text(
                text = airLevel,
                color = AIKTheme.colors.on_core_container,
                style = MaterialTheme.typography.body1
            )
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .width(60.dp)
                    .height(4.dp)
                    .background(color = airLevelColor)
            )
        }
    }
}

@Preview
@Composable
fun DailyForecastPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.EXCELLENT) {
        DailyForecast(
            dailyForecastDataList = listOf(
                DailyForecastData("Sunday", "12/16/2022", "Excellent", level1_core),
                DailyForecastData("Monday", "12/17/2022", "Good", level2_core),
                DailyForecastData("Tuesday", "12/18/2022", "Fine", level3_core),
                DailyForecastData("Wednesday", "12/19/2022", "Moderate", level4_core),
                DailyForecastData("Thursday", "12/20/2022", "Poor", level5_core),
                DailyForecastData("Friday", "12/21/2022", "Bad", level6_core),
                DailyForecastData("Saturday", "12/22/2022", "Unhealthy", level7_core)
            )
        )
    }
}