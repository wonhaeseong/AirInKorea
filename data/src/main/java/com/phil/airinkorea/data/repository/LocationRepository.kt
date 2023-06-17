package com.phil.airinkorea.data.repository

import com.google.android.gms.common.api.ResolvableApiException
import com.phil.airinkorea.data.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getSearchResult(
        query: String
    ): Flow<List<Location>>

    fun getUserLocationList(): Flow<List<Location>>
    suspend fun deleteUserLocation(userLocation: Location)
    suspend fun addUserLocation(userLocation: Location)
    fun getBookmark(): Flow<Location>
    suspend fun updateBookmark(newBookmark: Location, oldBookmark: Location)
    suspend fun removeBookmark(bookmark: Location)
    suspend fun addBookmark(bookmark: Location)
    fun getGPSLocation(): Flow<Location?>
    suspend fun fetchGPSLocation(latitude: Double, longitude: Double)
}
