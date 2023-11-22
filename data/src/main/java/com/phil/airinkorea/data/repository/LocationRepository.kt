package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.Page
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getSearchResult(
        query: String
    ): Flow<List<Location>>

    fun getCurrentPageStream(): Flow<Page>
    fun getCustomLocationListStream(): Flow<List<Location>>
    fun getBookmarkStream(): Flow<Location>
    fun getGPSLocationStream(): Flow<Location?>
    fun getSelectedLocationStream(): Flow<Location?>
    suspend fun deleteCustomLocation(location: Location)
    suspend fun addUserLocation(location: Location)
    suspend fun updateBookmark(newBookmark: Location, oldBookmark: Location)
    suspend fun fetchGPSLocationByCoordinate(latitude: Double, longitude: Double)
    suspend fun updatePage(newPage: Page)
}
