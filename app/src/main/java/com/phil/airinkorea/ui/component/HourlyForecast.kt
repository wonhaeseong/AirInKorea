package com.phil.airinkorea.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.theme.AikTypography
import com.phil.airinkorea.ui.theme.Shapes
//TODO: 컴포넌트 padding은 외부에서 Modifier를 이용해 정한다.
@Composable
fun AikHourlyForecast(
    hourlyForecastDatas: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 10.dp, end = 0.dp, bottom = 10.dp)
    ) {
        HourlyForecastBar()
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(
                start = 10.dp,
                end = 10.dp
            )
        ) {
            items(hourlyForecastDatas.size) { data ->
                HourlyForecastComponent()
            }
        }
    }
}

@Preview
@Composable
fun HourlyForecastBar() {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
    ) {
        Text(
            text = "Hourly Forecast",
            style = AikTypography.titleMedium,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun HourlyForecastComponent() {
    Card(
        shape = Shapes.medium,
        modifier = Modifier
            .wrapContentHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "AM10",
                style = AikTypography.titleSmall
            )
            Spacer(
                modifier = Modifier
                    .size(6.dp)
            )
            Text(
                text = "Unhealthy",
                style = AikTypography.bodyMedium
            )
            Spacer(
                modifier = Modifier
                    .size(12.dp)
            )
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(3.dp)
                    .background(Color.Blue)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF001FC5)
@Composable
fun AikHourlyForecastPreview() {
    AikHourlyForecast(
        hourlyForecastDatas = listOf(
            "d",
            "d",
            "d",
            "d",
            "d",
            "d",
            "d",
            "d",
            "d"
        )
    )
}