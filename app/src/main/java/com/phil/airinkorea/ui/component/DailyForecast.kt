package com.phil.airinkorea.ui.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.theme.AikTypography
import com.phil.airinkorea.ui.theme.level1_primaryContainer
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DailyForecastExpandableCard(
    expandedState: Boolean,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(level1_primaryContainer)
            .clickable { onClick() }
    ) {
        for (i in 0..2) {
            DailyForecastComponent(
                backgroundColor = Color.White,
                daysOfTheWeek = "Sunday",
                day = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.KOREA
                ).format(Calendar.getInstance().time),
                value = "Excellent",
                lineColor = Color.Blue
            )
        }
        AnimatedVisibility(expandedState) {
            Column() {
                //Todo: 3번 실행되는 이유 찾기
                for (i in 0..3) {
                    DailyForecastComponent(
                        backgroundColor = Color.White,
                        daysOfTheWeek = "Sunday",
                        day = SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.KOREA
                        ).format(Calendar.getInstance().time),
                        value = "Excellent",
                        lineColor = Color.Red
                    )
                    Log.d("dlrj","$i 생성됨")
                }
            }
        }
    }
}

@Preview
@Composable
fun DailyForecastBar() {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
    ) {
        Text(
            text = "Daily Forecast",
            style = AikTypography.titleMedium,
            color = Color.White
        )
    }
}

@Composable
fun DailyForecastComponent(
    backgroundColor: Color,
    daysOfTheWeek: String,
    day: String,
    value: String,
    lineColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(backgroundColor)
            .padding(5.dp)
    ) {
        Column {
            Text(
                text = daysOfTheWeek,
                style = AikTypography.bodyMedium
            )
            Text(
                text = day,
                color = Color.Gray,
                style = AikTypography.bodyMedium
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Text(
                text = value,
                style = AikTypography.bodyLarge
            )
            Spacer(
                modifier = Modifier
                    .size(6.dp)
            )
            Box(
                modifier = Modifier
                    .width(45.dp)
                    .height(3.dp)
                    .background(color = lineColor)
            )
        }
    }
}

@Preview
@Composable
fun DailyForecastComponentPreview() {
    DailyForecastComponent(
        backgroundColor = Color.White,
        daysOfTheWeek = "Sunday",
        day = SimpleDateFormat("dd/MM/yyyy", Locale.KOREA).format(Calendar.getInstance().time),
        value = "Excellent",
        lineColor = Color.Blue
    )
}

@Preview
@Composable
fun DailyForecastExpandableCardPreview() {
    var expandedState by remember {
        mutableStateOf(false)
    }
    DailyForecastExpandableCard(expandedState = expandedState) {
        expandedState = !expandedState
    }
}