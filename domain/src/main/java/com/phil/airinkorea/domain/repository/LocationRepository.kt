package com.phil.airinkorea.domain.repository

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.model.Result

interface LocationRepository {
    suspend fun getSearchResult(
        query: String? = null
    ): Result<List<Location>>

    suspend fun getUserLocationList(): Result<List<Location>>
    suspend fun getBookmark(): Result<Location>
    suspend fun changeBookmark(newBookmark: Location): Result<Unit>
    suspend fun deleteLocation(location: Location): Result<Unit>
    suspend fun addLocation(location: Location): Result<Unit>
}
