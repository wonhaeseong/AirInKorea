package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.model.Result
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(location:Location): Result<Unit> =
        locationRepository.deleteLocation(location)
}