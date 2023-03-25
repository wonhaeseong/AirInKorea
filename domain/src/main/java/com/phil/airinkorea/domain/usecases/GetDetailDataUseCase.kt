package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.Detail
import com.phil.airinkorea.domain.model.Result
import com.phil.airinkorea.domain.repository.AirDataRepository
import javax.inject.Inject

class GetDetailDataUseCase @Inject constructor(
    private val airDataRepository: AirDataRepository
){
    suspend operator fun invoke(station: String): Result<Detail> =
        airDataRepository.getDetailData(station)
}