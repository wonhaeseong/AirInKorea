package com.phil.airinkorea.domain.usecases.airdata

import com.phil.airinkorea.domain.repository.AirDataRepository
import javax.inject.Inject

class FetchAirDataUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
){
    suspend operator fun invoke(station:String) {
        airDataRepository.fetchAirData(station)
    }
}