package com.phil.airinkorea.domain.repository

import com.phil.airinkorea.domain.model.*

interface AirDataRepository {
    suspend fun getAirData(station: String): Result<AirData>
    suspend fun getKoreaForecastModelGif(): Result<KoreaForecastModelGif>
}