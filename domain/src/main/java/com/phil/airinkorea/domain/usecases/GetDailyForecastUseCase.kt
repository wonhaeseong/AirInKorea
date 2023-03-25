package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.DailyForecast
import com.phil.airinkorea.domain.model.Result
import com.phil.airinkorea.domain.repository.AirDataRepository
import javax.inject.Inject

class GetDailyForecastUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
) {
    suspend operator fun invoke(station: String): Result<List<DailyForecast>> =
        airDataRepository.getDailyForecast(station)
}