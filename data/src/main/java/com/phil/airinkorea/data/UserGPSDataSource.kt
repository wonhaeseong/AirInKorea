package com.phil.airinkorea.data

import com.phil.airinkorea.data.model.Coordinate

interface UserGPSDataSource {
    suspend fun getUserCurrentCoordinate():Coordinate?
}