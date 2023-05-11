package com.phil.airinkorea.domain.usecases.airdata

import com.phil.airinkorea.domain.model.KoreaForecastModelGif
import com.phil.airinkorea.domain.repository.AirDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKoreaForecastModelGifUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
) {
//    operator fun invoke(station: String): Flow<KoreaForecastModelGif> =
//        airDataRepository.getKoreaForecastModelGif(station)
}