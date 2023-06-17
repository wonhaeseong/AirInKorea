package com.phil.airinkorea.data.repository

import android.util.Log
import com.phil.airinkorea.data.database.dao.GPSLocationDao
import com.phil.airinkorea.data.database.dao.LocationDao
import com.phil.airinkorea.data.database.dao.UserLocationsDao
import com.phil.airinkorea.data.database.model.mapToExternalModel
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.mapToLocationEntity
import com.phil.airinkorea.data.model.mapToUserLocationEntity
import com.phil.airinkorea.data.network.firebase.FirebaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val gpsLocationDao: GPSLocationDao,
    private val userLocationsDao: UserLocationsDao,
    private val firebaseClient: FirebaseClient
) : LocationRepository {
    override fun getSearchResult(query: String): Flow<List<Location>> =
        locationDao.searchLocation(query).map { locationList ->
            locationList.map { it.mapToExternalModel() }
        }

    override fun getUserLocationList(): Flow<List<Location>> =
        userLocationsDao.getUserLocationList()
            .map { locationList ->
                locationList.map { it.mapToExternalModel() }
            }

    override suspend fun deleteUserLocation(userLocation: Location) {
        userLocationsDao.deleteUserLocationData(userLocation.mapToUserLocationEntity(false))

    }

    override suspend fun addUserLocation(userLocation: Location) {
        userLocationsDao.insertUserLocation(userLocation.mapToUserLocationEntity(false))
    }

    override fun getBookmark(): Flow<Location> =
        userLocationsDao.getBookmark().map {
            it?.mapToExternalModel() ?: Location(
                `do` = "Seoul",
                sigungu = "Yongsan-gu",
                eupmyeondong = "Namyeong-dong",
                station = "한강대로"
            )
        }

    override suspend fun updateBookmark(newBookmark: Location, oldBookmark: Location) {
        userLocationsDao.updateUserLocation(
            oldBookmark.mapToUserLocationEntity(false)
        )
        userLocationsDao.updateUserLocation(
            newBookmark.mapToUserLocationEntity(true)
        )
    }

    override suspend fun removeBookmark(bookmark: Location) {
        userLocationsDao.updateUserLocation(bookmark.mapToUserLocationEntity(false))
    }

    override suspend fun addBookmark(bookmark: Location) {
        userLocationsDao.updateUserLocation(bookmark.mapToUserLocationEntity(true))
    }


    override fun getGPSLocation(): Flow<Location?> =
        gpsLocationDao.getGPSLocation().map { it?.mapToExternalModel() }

    override suspend fun fetchGPSLocation(latitude: Double, longitude: Double) {
        val location = firebaseClient.getLocationByCoordinate(latitude, longitude)?.mapToLocationEntity()
        if (location == null) {
            gpsLocationDao.deleteGPSLocation()
        } else {
            Log.d("TAG fetchGPS", location.toString())
            val isLocationExists = withContext(Dispatchers.IO) {
                gpsLocationDao.isExists()
            }
            if (isLocationExists) {
                gpsLocationDao.updateGPSLocation(
                    enDo = location.enDo,
                    enSigungu = location.enSigungu,
                    enEupmyeondong = location.enEupmyeondong,
                    station = location.station
                )
            } else {
                gpsLocationDao.insertGPSLocation(
                    location
                )
            }
        }
    }
}
