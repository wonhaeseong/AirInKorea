package com.phil.airinkorea.domain.model

data class AirData(
    val station: String,
    val pm10Value: String?,
    val pm25Value: String?,
    val so2Value: String?,
    val coValue: String?,
    val o3Value: String?,
    val khaiValue: String?,
    val no2Value: String?,
    val pm10Flag: String?,
    val pm25Flag: String?,
    val coFlag: String?,
    val no2Flag: String?,
    val o3Flag: String?,
    val so2Flag: String?,
    val so2Grade: String?,
    val o3Grade: String?,
    val coGrade: String?,
    val pm10Grade: String?,
    val khaiGrade: String?,
    val no2Grade: String?,
    val dataTime: String?
)