package com.phil.airinkorea.domain.repository

import com.phil.airinkorea.domain.model.*

interface AirDataRepository {
    suspend fun getDailyForecast(station: String): Result<List<DailyForecast>>
    suspend fun getHourlyForecast(station: String): Result<List<HourlyForecast>>
    suspend fun getDetailData(station: String): Result<Detail>
    suspend fun getAirLevel(station:String): Result<AirInfo>
}