package com.phil.airinkorea.domain.usecases.user

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import javax.inject.Inject

class AddUserLocationUseCase @Inject constructor(
    private val locationRepository: com.phil.airinkorea.data.repository.LocationRepository
){
    suspend operator fun invoke(userLocation: com.phil.airinkorea.data.model.Location) =
        locationRepository.addUserLocation(userLocation = userLocation)
}