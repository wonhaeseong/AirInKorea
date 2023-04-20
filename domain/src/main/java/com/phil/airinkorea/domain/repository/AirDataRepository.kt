package com.phil.airinkorea.domain.repository

import com.phil.airinkorea.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AirDataRepository {
    fun getAirData(station: String):Flow<AirData>
    suspend fun fetchAirData(station: String)
    suspend fun getKoreaForecastModelGif(station: String):KoreaForecastModelGif
}