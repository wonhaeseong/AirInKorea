package com.phil.airinkorea.domain.usecases.user

import com.phil.airinkorea.domain.model.UserLocation
import com.phil.airinkorea.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLocationListUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<List<UserLocation>> =
        locationRepository.getUserLocationList()
}