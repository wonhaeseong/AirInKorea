package com.phil.airinkorea.domain.usecases.bookmark

import com.phil.airinkorea.domain.model.UserLocation
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject

class UpdateBookmarkUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(newBookmark: UserLocation, oldBookmark: UserLocation) =
        locationRepository.updateBookmark(newBookmark, oldBookmark)
}