package com.phil.airinkorea.domain.usecases.search

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(query:String): Flow<List<Location>> =
        locationRepository.getSearchResult(query)
}