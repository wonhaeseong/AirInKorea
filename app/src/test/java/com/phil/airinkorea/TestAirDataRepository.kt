package com.phil.airinkorea

import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.repository.AirDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestAirDataRepository : AirDataRepository{
    override fun getAirData(station: String): Flow<AirData> = flow {
    }
}