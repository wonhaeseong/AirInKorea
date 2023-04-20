package com.phil.airinkorea.domain.usecases.bookmark

import com.phil.airinkorea.domain.model.UserLocation
import com.phil.airinkorea.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke(): Flow<UserLocation> =
        locationRepository.getBookmark()
}