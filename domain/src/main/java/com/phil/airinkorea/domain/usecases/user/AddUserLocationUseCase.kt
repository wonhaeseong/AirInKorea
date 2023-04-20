package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.UserLocation
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject

class AddUserLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(userLocation: UserLocation) =
        locationRepository.addUserLocation(userLocation = userLocation)
}