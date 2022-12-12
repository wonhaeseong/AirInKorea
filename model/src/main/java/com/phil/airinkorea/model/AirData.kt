package com.phil.airinkorea.model

data class AirData(
    val pm10Value: String?,
    val pm25Value: String?,
    val so2Value: String?,
    val coValue: String?,
    val o3Value: String?,
    val no2Value: String?,
    val pm10Flag: String?,
    val pm25Flag: String?,
    val so2Grade: String?,
    val coFlag: String?,
    val o3Grade: String?,
    val khaiValue: String?,
    val khaiGrade: String?,
    val no2Flag: String?,
    val no2Grade: String?,
    val o3Flag: String?,
    val so2Flag: String?,
    val dataTime: String?,
    val coGrade: String?,
    val pm10Grade: String?
)
