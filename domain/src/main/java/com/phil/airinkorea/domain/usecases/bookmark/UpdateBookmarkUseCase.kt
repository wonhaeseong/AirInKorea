package com.phil.airinkorea.domain.usecases.bookmark

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import javax.inject.Inject

class UpdateBookmarkUseCase @Inject constructor(
    private val locationRepository: com.phil.airinkorea.data.repository.LocationRepository
) {
    suspend operator fun invoke(newBookmark: com.phil.airinkorea.data.model.Location, oldBookmark: com.phil.airinkorea.data.model.Location) =
        locationRepository.updateBookmark(newBookmark, oldBookmark)
}