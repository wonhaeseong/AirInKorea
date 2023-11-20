package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.AirData
import kotlinx.coroutines.flow.Flow

interface AirDataRepository {
    fun getAirDataStream(): Flow<AirData>
    suspend fun updateAirData(station: String)
}