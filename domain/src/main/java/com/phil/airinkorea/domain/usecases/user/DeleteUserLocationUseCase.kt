package com.phil.airinkorea.domain.usecases.user

import com.phil.airinkorea.domain.model.UserLocation
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject

class DeleteUserLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(userLocation: UserLocation) =
        locationRepository.deleteUserLocation(userLocation)
}