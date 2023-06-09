package com.phil.airinkorea.domain.usecases.airdata

import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.repository.AirDataRepository
import javax.inject.Inject

class GetAirDataUseCase @Inject constructor(
    private val airDataRepository: com.phil.airinkorea.data.repository.AirDataRepository
) {
    suspend operator fun invoke(station: String): com.phil.airinkorea.data.model.AirData {
        return airDataRepository.getAirData(station)
    }
}