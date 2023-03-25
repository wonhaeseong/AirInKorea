package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.HourlyForecast
import com.phil.airinkorea.domain.model.Result
import com.phil.airinkorea.domain.repository.AirDataRepository
import javax.inject.Inject

class GetHourlyForecastUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
){
    suspend operator fun invoke(station:String): Result<List<HourlyForecast>> =
        airDataRepository.getHourlyForecast(station)
}