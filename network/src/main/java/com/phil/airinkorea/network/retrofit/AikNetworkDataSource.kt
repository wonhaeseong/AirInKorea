package com.phil.airinkorea.network.retrofit

import com.phil.airinkorea.network.model.NetworkAirData
import com.phil.airinkorea.network.model.NetworkResultBody
import com.phil.airinkorea.network.model.NetworkLocation
import com.phil.airinkorea.network.model.NetworkNearbyStation

interface AikNetworkDataSource {
    suspend fun getStationLocation(
        serviceKey: String?,
        returnType: String = "json",
        numOfRows: Int = 1000,
        umdName: String?
    ): NetworkResultBody<NetworkLocation>

    suspend fun getNearbyStation(
        serviceKey: String?,
        returnType: String = "json",
        tmX: String?,
        tmY: String?
    ): NetworkResultBody<NetworkNearbyStation>

    suspend fun getAirData(
        serviceKey: String?,
        returnType: String = "json",
        numOfRows: Int = 1,
        stationName: String?,
        dataTerm: String = "DAILY"
    ): NetworkResultBody<NetworkAirData>
}