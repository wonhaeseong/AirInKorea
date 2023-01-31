package com.phil.airinkorea.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Preview
@Composable
fun AikDailyForecast() {
    var expandedState by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DailyForecastBar(
            modifier = Modifier
                .padding(start = 10.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        DailyForecastExpandableCard(
            expandedState = expandedState,
            modifier = Modifier
                .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
        ) {
            expandedState = !expandedState
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyForecastExpandableCard(
    modifier: Modifier = Modifier,
    expandedState: Boolean,
    backgroundColor: Color = AIKTheme.colors.core,
    onClick: () -> Unit
) {
    Card(
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick
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
                }
            }
        }
    }
}

@Preview
@Composable
fun DailyForecastBar(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
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