package com.phil.airinkorea.domain.usecases

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(
        query:String?
    ): List<Location> = locationRepository.getSearchResult(
        query = query
    )
}