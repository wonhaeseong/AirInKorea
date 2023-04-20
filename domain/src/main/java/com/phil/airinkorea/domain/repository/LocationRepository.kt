package com.phil.airinkorea.domain.repository

import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.model.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getSearchResult(
        query: String
    ): Flow<List<Location>>
    fun getUserLocationList(): Flow<List<UserLocation>>
    suspend fun deleteUserLocation(userLocation: UserLocation)
    suspend fun addUserLocation(userLocation: UserLocation)
    fun getBookmark(): Flow<UserLocation>
    suspend fun updateBookmark(newBookmark: UserLocation, oldBookmark: UserLocation)
    suspend fun removeBookmark(bookmark: UserLocation)
    suspend fun addBookmark(bookmark: UserLocation)
    fun getGPSLocation(): Flow<Location>
    suspend fun fetchGPSLocation(oldLocation: Location, newLocation: Location)
}
