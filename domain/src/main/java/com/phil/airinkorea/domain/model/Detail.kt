package com.phil.airinkorea.domain.model

data class Detail(
    val pm25Level: String,
    val pm25Value: Int,
    val pm10Level: String,
    val pm10Value: Int,
    val no2Level: String,
    val no2Value: Int,
    val so2Level: String,
    val so2Value: Int,
    val coLevel: String,
    val coValue: Int,
    val o3Level: String,
    val o3Value: Int
)
