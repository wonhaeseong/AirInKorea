package com.phil.airinkorea.data.repository

import android.util.Log
import com.phil.airinkorea.data.database.dao.AirDataDao
import com.phil.airinkorea.data.database.dao.UserLocationsDao
import com.phil.airinkorea.data.database.model.mapToExternalModel
import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.model.mapToAirDataEntity
import com.phil.airinkorea.data.network.firebase.FirebaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AirDataRepositoryImpl @Inject constructor(
    private val airDataDao: AirDataDao,
    private val userLocationsDao: UserLocationsDao,
    private val networkDataSource: FirebaseClient//인터페이스
) : AirDataRepository {
    override fun getAirDataStream(): Flow<AirData> =
        airDataDao.getAirDataForSelectedUserLocations().map {
            it?.mapToExternalModel(
                userLocationsDao.getSelectedLocation()?.mapToExternalModel()
            ) ?: AirData(
                userLocation = null,
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
        }.flowOn(Dispatchers.IO).onEach {
            Log.d("airstream", it.toString())
        }

    override suspend fun updateAirData(station: String): Unit =
        withContext(Dispatchers.IO) {
            networkDataSource.getAirData(station)?.let {
                airDataDao.upsertAirData(
                    it.mapToAirDataEntity(station)
                )
            }
        }
}