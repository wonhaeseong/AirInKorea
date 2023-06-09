package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import kotlinx.coroutines.flow.Flow

interface AirDataRepository {
    fun getAirData(station: String?): Flow<AirData>
    suspend fun fetchAirData(station: String)
}