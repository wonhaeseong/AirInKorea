package com.phil.airinkorea

import com.google.android.gms.common.api.ResolvableApiException
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class TestLocationRepository : LocationRepository{
    override fun getSearchResult(query: String): Flow<List<Location>> {
        TODO("Not yet implemented")
    }

    override fun getUserLocationList(): Flow<List<Location>> {
        TODO("Not yet implemented")
    }


    override suspend fun deleteUserLocation(userLocation: Location) {
        TODO("Not yet implemented")
    }

    override suspend fun addUserLocation(userLocation: Location) {
        TODO("Not yet implemented")
    }

    override fun getBookmark(): Flow<Location> {
        TODO("Not yet implemented")
    }


    override suspend fun updateBookmark(newBookmark: Location, oldBookmark: Location) {
        TODO("Not yet implemented")
    }

    override suspend fun removeBookmark(bookmark: Location) {
        TODO("Not yet implemented")
    }

    override suspend fun addBookmark(bookmark: Location) {
        TODO("Not yet implemented")
    }

    override fun getGPSLocation(): Flow<Location?> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchGPSLocation(getAirDataCallback: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun checkGPSOn(): ResolvableApiException? {
        TODO("Not yet implemented")
    }
}