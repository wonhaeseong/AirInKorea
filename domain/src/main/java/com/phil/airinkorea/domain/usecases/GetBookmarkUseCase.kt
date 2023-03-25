package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.model.Result
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject

class GetBookmarkUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    suspend operator fun invoke(): Result<Location> =
        locationRepository.getBookmark()
}