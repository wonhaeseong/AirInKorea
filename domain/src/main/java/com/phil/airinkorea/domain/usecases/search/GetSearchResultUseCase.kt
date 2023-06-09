package com.phil.airinkorea.domain.usecases.search

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val locationRepository: com.phil.airinkorea.data.repository.LocationRepository
) {
    operator fun invoke(query:String): Flow<List<com.phil.airinkorea.data.model.Location>> =
        locationRepository.getSearchResult(query)
}