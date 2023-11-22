package com.phil.airinkorea.data

import com.phil.airinkorea.data.network.model.NetworkAirData
import com.phil.airinkorea.data.network.model.NetworkCoordinateResult

interface NetworkDataSource {
    suspend fun getAirData(station: String): NetworkAirData?
    suspend fun getAirDataByCoordinate(
        latitude: Double,
        longitude: Double
    ): NetworkCoordinateResult?
}