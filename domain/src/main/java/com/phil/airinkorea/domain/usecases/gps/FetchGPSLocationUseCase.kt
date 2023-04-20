package com.phil.airinkorea.domain.usecases.gps

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject

class FetchGPSLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(oldLocation: Location, newLocation: Location) {
        locationRepository.fetchGPSLocation(
            oldLocation = oldLocation,
            newLocation = newLocation
        )
    }
}