package com.phil.airinkorea.domain.usecases.bookmark

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkUseCase @Inject constructor(
    private val locationRepository: com.phil.airinkorea.data.repository.LocationRepository
){
    operator fun invoke(): Flow<com.phil.airinkorea.data.model.Location> =
        locationRepository.getBookmark()
}