package com.phil.airinkorea.domain.usecases.bookmark

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(
    private val locationRepository: com.phil.airinkorea.data.repository.LocationRepository
){
    suspend operator fun invoke(bookmark: com.phil.airinkorea.data.model.Location) {
        locationRepository.addBookmark(bookmark)
    }
}