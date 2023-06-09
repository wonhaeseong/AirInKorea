package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.data.repository.LocationRepository
import javax.inject.Inject

class GetDrawerLocations @Inject constructor(
    locationRepository: com.phil.airinkorea.data.repository.LocationRepository
) {
    suspend operator fun invoke() {

    }
}