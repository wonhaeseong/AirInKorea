package com.phil.airinkorea.domain.usecases.user

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLocationListUseCase @Inject constructor(
    private val locationRepository: com.phil.airinkorea.data.repository.LocationRepository
) {
    operator fun invoke(): Flow<List<com.phil.airinkorea.data.model.Location>> =
        locationRepository.getUserLocationList()
}