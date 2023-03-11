package com.phil.airinkorea.domain.repository

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.model.Result

interface LocationRepository {
    suspend fun getSearchResult(
        query: String? = null
    ): List<Location>
}
