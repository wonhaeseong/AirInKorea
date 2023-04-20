package com.phil.airinkorea.domain.usecases.bookmark

import com.phil.airinkorea.domain.model.UserLocation
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(bookmark: UserLocation) {
        locationRepository.addBookmark(bookmark)
    }
}