package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.mapToGPSLocationEntity
import com.phil.airinkorea.data.model.mapToUserLocationEntity
import com.phil.airinkorea.database.dao.GPSLocationDao
import com.phil.airinkorea.database.dao.LocationDao
import com.phil.airinkorea.database.dao.UserLocationsDao
import com.phil.airinkorea.database.model.mapToExternalModel
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.model.UserLocation
import com.phil.airinkorea.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val gpsLocationDao: GPSLocationDao,
    private val userLocationsDao: UserLocationsDao
) : LocationRepository {
    override fun getSearchResult(query: String): Flow<List<Location>> =
        locationDao.searchLocation(query).map { locationList ->
            locationList.map { it.mapToExternalModel() }
        }

    override fun getUserLocationList(): Flow<List<UserLocation>> =
        userLocationsDao.getUserLocationList().map { userLocationList ->
            userLocationList.map { it.mapToExternalModel() }
        }

    override suspend fun deleteUserLocation(userLocation: UserLocation) {
        userLocationsDao.deleteUserLocationData(userLocation.mapToUserLocationEntity())
    }

    override suspend fun addUserLocation(userLocation: UserLocation) {
        userLocationsDao.insertUserLocation(userLocation.mapToUserLocationEntity())
    }

    override fun getBookmark(): Flow<UserLocation> =
        userLocationsDao.getBookmark().map { it.mapToExternalModel() }

    override suspend fun updateBookmark(newBookmark: UserLocation, oldBookmark: UserLocation) {
        newBookmark.bookmark = true
        oldBookmark.bookmark = false
        userLocationsDao.updateUserLocation(
            oldBookmark.mapToUserLocationEntity()
        )
        userLocationsDao.updateUserLocation(
            newBookmark.mapToUserLocationEntity()
        )
    }

    override suspend fun removeBookmark(bookmark: UserLocation) {
        bookmark.bookmark = false
        userLocationsDao.updateUserLocation(bookmark.mapToUserLocationEntity())
    }

    override suspend fun addBookmark(bookmark: UserLocation) {
        bookmark.bookmark = true
        userLocationsDao.updateUserLocation(bookmark.mapToUserLocationEntity())
    }


    override fun getGPSLocation(): Flow<Location> =
        gpsLocationDao.getGPSLocation().map { it.mapToExternalModel() }


    override suspend fun fetchGPSLocation(oldLocation: Location,newLocation: Location ) {
        gpsLocationDao.deleteAndInsertGPSLocation(
            oldData = oldLocation.mapToGPSLocationEntity(),
            newData = newLocation.mapToGPSLocationEntity()
        )
    }
}
