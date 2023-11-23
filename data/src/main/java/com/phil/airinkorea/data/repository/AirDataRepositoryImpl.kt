package com.phil.airinkorea.data.repository

import android.util.Log
import com.phil.airinkorea.data.LocalPageStore
import com.phil.airinkorea.data.NetworkDataSource
import com.phil.airinkorea.data.UserGPSDataSource
import com.phil.airinkorea.data.database.dao.AirDataDao
import com.phil.airinkorea.data.database.dao.UserLocationsDao
import com.phil.airinkorea.data.database.model.mapToExternalModel
import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.model.Page
import com.phil.airinkorea.data.model.mapToAirDataEntity
import com.phil.airinkorea.data.model.mapToUserLocationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AirDataRepositoryImpl @Inject constructor(
    private val airDataDao: AirDataDao,
    private val userLocationsDao: UserLocationsDao,
    private val networkDataSource: NetworkDataSource,
    private val localPageStore: LocalPageStore,
    private val userGPSDataSource: UserGPSDataSource
) : AirDataRepository {
    private val noAirData = AirData(
        date = null,
        airLevel = AirLevel.LevelError,
        detailAirData = DetailAirData(
            pm25Level = AirLevel.LevelError,
            pm25Value = null,
            pm10Level = AirLevel.LevelError,
            pm10Value = null,
            no2Level = AirLevel.LevelError,
            no2Value = null,
            so2Level = AirLevel.LevelError,
            so2Value = null,
            coLevel = AirLevel.LevelError,
            coValue = null,
            o3Level = AirLevel.LevelError,
            o3Value = null
        ),
        dailyForecast = arrayListOf(),
        information = null,
        koreaForecastModelGif = KoreaForecastModelGif(
            pm10GifUrl = null,
            pm25GifUrl = null
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAirDataStream(): Flow<AirData> =
        localPageStore.getCurrentPageStream().flatMapLatest { page ->
            when (page) {
                Page.GPS -> {
                    airDataDao.getAirDataForGPS().map { it?.mapToExternalModel() ?: noAirData }
                }

                Page.Bookmark -> airDataDao.getAirDataForBookmark()
                    .map { it?.mapToExternalModel() ?: noAirData }

                is Page.CustomLocation -> {
                    val location = userLocationsDao.getCustomLocationList()[page.pageNum]
                    airDataDao.getAirDataForCustomLocations(location.station)
                        .map { it?.mapToExternalModel() ?: noAirData }
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun updateAirData(page: Page) {
        withContext(Dispatchers.IO) {
            when (page) {
                Page.GPS -> {
                    userGPSDataSource.getUserCurrentCoordinate()?.let {
                        fetchGPSLocationByCoordinate(it.latitude, it.longitude)
                    }
                }

                Page.Bookmark -> {
                    userLocationsDao.getBookmarkStream().first()?.mapToExternalModel()?.let {
                        updateAirData(it.station)
                    }
                }

                is Page.CustomLocation -> {
                    userLocationsDao.getCustomLocationListStream()
                        .first()[page.pageNum].mapToExternalModel().let {
                        updateAirData(it.station)
                    }
                }
            }
        }
    }

    private suspend fun updateAirData(station: String) {
        networkDataSource.getAirData(station)?.let { networkAirData ->
            airDataDao.upsertAirData(
                networkAirData.mapToAirDataEntity(station)
            )
        }
    }

    private suspend fun fetchGPSLocationByCoordinate(latitude: Double, longitude: Double) =
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
                        locationEntity.mapToUserLocationEntity(isGPS = true)
                    )
                }
                airDataDao.upsertAirData(
                    networkAirData.mapToAirDataEntity(locationEntity.station)
                )
            }
        }
}