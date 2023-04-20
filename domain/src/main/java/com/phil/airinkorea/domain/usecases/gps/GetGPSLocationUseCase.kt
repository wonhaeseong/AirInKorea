package com.phil.airinkorea.domain.usecases.gps

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGPSLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke() : Flow<Location> =
        locationRepository.getGPSLocation()
}