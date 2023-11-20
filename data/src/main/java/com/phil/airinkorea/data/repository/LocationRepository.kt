package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.SelectedLocation
import com.phil.airinkorea.data.model.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getSearchResult(
        query: String
    ): Flow<List<Location>>

    fun getCustomLocationListStream(): Flow<List<UserLocation>>
    fun getBookmarkStream(): Flow<UserLocation>
    fun getGPSLocationStream(): Flow<UserLocation?>
    fun getSelectedLocationStream(): Flow<UserLocation?>
    suspend fun deleteUserLocation(userLocation: UserLocation)
    suspend fun addUserLocation(location: Location)
    suspend fun updateBookmark(newBookmark: UserLocation, oldBookmark: UserLocation)
    suspend fun fetchGPSLocationByCoordinate(latitude: Double, longitude: Double)
    suspend fun selectLocation(newLocation: UserLocation?, oldLocation: UserLocation?)
    suspend fun selectBookmark()
}
