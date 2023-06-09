package com.phil.airinkorea.domain.usecases.airdata

import com.phil.airinkorea.data.repository.AirDataRepository
import javax.inject.Inject

class GetKoreaForecastModelGifUseCase @Inject constructor(
    private val airDataRepository: com.phil.airinkorea.data.repository.AirDataRepository
) {
//    operator fun invoke(station: String): Flow<KoreaForecastModelGif> =
//        airDataRepository.getKoreaForecastModelGif(station)
}