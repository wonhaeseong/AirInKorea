package com.phil.airinkorea.domain.usecases.airdata

import com.phil.airinkorea.domain.model.AirData
import com.phil.airinkorea.domain.repository.AirDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAirDataUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
) {
    operator fun invoke(station:String): Flow<AirData> {
        return airDataRepository.getAirData(station)
    }
}