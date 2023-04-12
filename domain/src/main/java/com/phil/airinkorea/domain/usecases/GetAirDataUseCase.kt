package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.AirData
import com.phil.airinkorea.domain.model.Result
import com.phil.airinkorea.domain.repository.AirDataRepository
import javax.inject.Inject

class GetAirDataUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
) {
    suspend operator fun invoke(station:String): Result<AirData> =
    airDataRepository.getAirData(station)
}