package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.model.HourlyForecastData
import com.phil.airinkorea.ui.commoncomponents.TitleBar
import com.phil.airinkorea.ui.theme.*

@Composable
fun HourlyForecast(
    hourlyForecastDataList: List<HourlyForecastData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 10.dp)
    ) {
        TitleBar(titleText = "Hourly Forecast")
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(
                start = 10.dp,
                end = 10.dp
            )
        ) {
            items(hourlyForecastDataList) { data ->
                HourlyForecastComponent(data)
            }
        }
    }
}

@Composable
fun HourlyForecastComponent(
    hourlyForecastData: HourlyForecastData,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = Shapes.medium,
        color = AIKTheme.colors.core_container,
        elevation = 2.dp,
        modifier = modifier
            .size(85.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = hourlyForecastData.time,
                style = MaterialTheme.typography.subtitle3,
                color = AIKTheme.colors.on_core_container
            )
            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )
            Text(
                text = hourlyForecastData.airLevel,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.SemiBold,
                color = AIKTheme.colors.on_core_container
            )
            Spacer(
                modifier = Modifier
                    .size(11.dp)
            )
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .width(40.dp)
                    .height(4.dp)
                    .background(hourlyForecastData.airLevelColor)

            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF001FC5)
@Composable
fun HourlyForecastPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.GOOD) {
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
                ),
            )
        )
    }
}