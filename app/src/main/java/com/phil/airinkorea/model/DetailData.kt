package com.phil.airinkorea.model

import androidx.compose.ui.graphics.Color

data class DetailData(
    val pm25Level: String,
    val pm25Value: Int,
    val pm25Color: Color,
    val pm10Level: String,
    val pm10Value: Int,
    val pm10Color: Color,
    val no2Level: String,
    val no2Value: Int,
    val no2Color: Color,
    val so2Level: String,
    val so2Value: Int,
    val so2Color: Color,
    val coLevel: String,
    val coValue: Int,
    val coColor: Color,
    val o3Level: String,
    val o3Value: Int,
    val o3Color: Color
)
