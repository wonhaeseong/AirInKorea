package com.phil.airinkorea.domain.usecases.gps

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import javax.inject.Inject

class GetGPSLocationUseCase @Inject constructor(
    private val locationRepository: com.phil.airinkorea.data.repository.LocationRepository
){
    suspend operator fun invoke() : com.phil.airinkorea.data.model.Location? =
        locationRepository.getGPSLocation()
}