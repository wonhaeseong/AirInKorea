package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.AirInfo
import com.phil.airinkorea.domain.model.Result
import com.phil.airinkorea.domain.repository.AirDataRepository
import javax.inject.Inject

class GetAirInfoUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
) {
    suspend operator fun invoke(station: String): Result<AirInfo> =
        airDataRepository.getAirLevel(station)
}