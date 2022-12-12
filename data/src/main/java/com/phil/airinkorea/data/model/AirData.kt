package com.phil.airinkorea.data.model

import com.phil.airinkorea.database.model.AirDataEntity
import com.phil.airinkorea.network.model.NetworkAirData

fun NetworkAirData.asEntity() = AirDataEntity(
    pm10Value = pm10Value,
    pm25Value = pm25Value,
    so2Value = so2Value,
    coValue = coValue,
    o3Value = o3Value,
    no2Value = no2Value,
    pm10Flag = pm10Flag,
    pm25Flag = pm25Flag,
    so2Grade = so2Grade,
    coFlag = coFlag,
    o3Grade = o3Grade,
    khaiValue = khaiValue,
    khaiGrade = khaiGrade,
    no2Flag = no2Flag,
    no2Grade = no2Grade,
    o3Flag = o3Flag,
    so2Flag = so2Flag,
    dataTime = dataTime,
    coGrade = coGrade,
    pm10Grade = pm10Grade
)