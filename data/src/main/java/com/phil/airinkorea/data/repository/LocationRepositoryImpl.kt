package com.phil.airinkorea.data.repository

import android.util.Log
import com.phil.airinkorea.data.database.dao.AirDataDao
import com.phil.airinkorea.data.database.dao.LocationDao
import com.phil.airinkorea.data.database.dao.UserLocationsDao
import com.phil.airinkorea.data.database.model.mapToExternalModel
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.UserLocation
import com.phil.airinkorea.data.model.mapToAirDataEntity
import com.phil.airinkorea.data.model.mapToUserLocationEntity
import com.phil.airinkorea.data.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val userLocationsDao: UserLocationsDao,
    private val airDataDao: AirDataDao,
    private val networkDataSource: NetworkDataSource
) : LocationRepository {
    override fun getSearchResult(query: String): Flow<List<Location>> =
        locationDao.searchLocation(query).map { locationList ->
            locationList.map { it.mapToExternalModel() }
        }.flowOn(Dispatchers.IO)

    override fun getCustomLocationListStream(): Flow<List<UserLocation>> =
        userLocationsDao.getCustomLocationListStream().map { locationList ->
            locationList.map { it.mapToExternalModel() }
        }.flowOn(Dispatchers.IO).onEach { Log.d("a:UserLocation", it.toString()) }

    override fun getGPSLocationStream(): Flow<UserLocation?> =
        userLocationsDao.getGPSLocationStream().map { it?.mapToExternalModel() }
            .flowOn(Dispatchers.IO).onEach { Log.d("a:GPSLocation", it.toString()) }

    override fun getSelectedLocationStream(): Flow<UserLocation?> =
        userLocationsDao.getSelectedLocationStream().map { it?.mapToExternalModel() }

    override fun getBookmarkStream(): Flow<UserLocation> =
        userLocationsDao.getBookmarkStream().map {
            it?.mapToExternalModel() ?: UserLocation(
                Location(
                    `do` = "Seoul",
                    sigungu = "Yongsan-gu",
                    eupmyeondong = "Namyeong-dong",
                    station = "한강대로"
                ), isBookmark = true, isGPS = false, isSelected = false
            ).also { defaultLocation ->
                userLocationsDao.insertUserLocation(
                    defaultLocation.mapToUserLocationEntity()
                )
            }
        }.flowOn(Dispatchers.IO).onEach { Log.d("a:BookmarkLocation", it.toString()) }

    override suspend fun deleteUserLocation(userLocation: UserLocation): Unit =
        withContext(Dispatchers.IO) {
            userLocationsDao.deleteUserLocation(userLocation.mapToUserLocationEntity())
            //check GPS is null and if it's not null Select bookmark
            if (userLocationsDao.getSelectedLocation() == null) {
                selectBookmark()
            }
        }

    override suspend fun addUserLocation(location: Location) = withContext(Dispatchers.IO) {
        userLocationsDao.insertUserLocation(location.mapToUserLocationEntity())
    }

    override suspend fun updateBookmark(newBookmark: UserLocation, oldBookmark: UserLocation) =
        withContext(Dispatchers.IO) {
            userLocationsDao.updateUserLocation(
                oldBookmark.copy(isBookmark = false).mapToUserLocationEntity()
            )
            userLocationsDao.updateUserLocation(
                newBookmark.copy(isBookmark = true).mapToUserLocationEntity()
            )
        }

    override suspend fun fetchGPSLocationByCoordinate(latitude: Double, longitude: Double) =
        withContext(Dispatchers.IO) {
            val networkResult = networkDataSource.getAirDataByCoordinate(latitude, longitude)
            val locationEntity = networkResult?.networkLocation
            val networkAirData = networkResult?.networkAirData
            Log.d("TAG fetchGPS", locationEntity.toString())
            if (networkAirData != null && locationEntity != null) {
                if (userLocationsDao.isGPSExist()) {
                    userLocationsDao.updateGPSLocation(
                        enDo = locationEntity.`do`,
                        sigungu = locationEntity.sigungu,
                        eupmyeondong = locationEntity.eupmyeondong,
                        station = locationEntity.station
                    )
                } else {
                    userLocationsDao.insertUserLocation(
                        locationEntity.mapToUserLocationEntity(isGPS = true, isSelected = true)
                    )
                }
                airDataDao.upsertAirData(
                    networkAirData.mapToAirDataEntity(locationEntity.station)
                )
            }
        }

    override suspend fun selectLocation(newLocation: UserLocation?, oldLocation: UserLocation?) =
        withContext(Dispatchers.IO) {
            if (oldLocation != null && newLocation != null) {
                userLocationsDao.updateUserLocation(
                    oldLocation.copy(isSelected = false).mapToUserLocationEntity()
                )
                userLocationsDao.updateUserLocation(
                    newLocation.copy(isSelected = true).mapToUserLocationEntity()
                )
            } else if (newLocation == null && oldLocation != null) {
                userLocationsDao.updateUserLocation(
                    oldLocation.copy(isSelected = false).mapToUserLocationEntity()
                )
            } else if (newLocation != null) {
                userLocationsDao.updateUserLocation(
                    newLocation.copy(isSelected = true).mapToUserLocationEntity()
                )
            }
        }

    override suspend fun selectBookmark(): Unit = withContext(Dispatchers.IO) {
        userLocationsDao.getBookmarkStream().first()
            ?.let {
                userLocationsDao.updateUserLocation(it.copy(isSelected = 1))
                networkDataSource.getAirData(it.station)?.let { networkAirData ->
                    airDataDao.upsertAirData(
                        networkAirData.mapToAirDataEntity(it.station)
                    )
                }
            }
    }
}
