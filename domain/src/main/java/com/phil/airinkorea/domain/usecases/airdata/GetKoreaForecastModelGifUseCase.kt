package com.phil.airinkorea.domain.usecases.airdata

import com.phil.airinkorea.domain.model.KoreaForecastModelGif
import com.phil.airinkorea.domain.repository.AirDataRepository
import javax.inject.Inject

class GetKoreaForecastModelGifUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
) {
    suspend operator fun invoke(station: String): KoreaForecastModelGif =
        airDataRepository.getKoreaForecastModelGif(station)
}