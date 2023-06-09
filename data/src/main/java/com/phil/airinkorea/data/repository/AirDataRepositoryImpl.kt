package com.phil.airinkorea.data.repository

import android.util.Log
import com.phil.airinkorea.data.database.dao.AirDataDao
import com.phil.airinkorea.data.database.model.AirDataEntity
import com.phil.airinkorea.data.database.model.DetailAirDataEntity
import com.phil.airinkorea.data.database.model.KoreaForecastModelGifEntity
import com.phil.airinkorea.data.database.model.mapToExternalModel
import com.phil.airinkorea.data.model.AirData
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.model.mapToAirDataEntity
import com.phil.airinkorea.data.network.firebase.FirebaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AirDataRepositoryImpl @Inject constructor(
    private val airDataDao: AirDataDao,
    private val firebaseClient: FirebaseClient
) : AirDataRepository {
    override fun getAirData(station: String?): Flow<AirData> =
        station?.let {
            airDataDao.getAirData(it).map { airDataEntity ->
                airDataEntity.mapToExternalModel()
            }
        } ?: flow {
            emit(
                AirData(
                    station = station,
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
            )
        }

    override suspend fun fetchAirData(station: String) {
        Log.d("TAGfetchAirData", station)
        val networkAirData = withContext(Dispatchers.IO) {
            firebaseClient.getAirData(station)
        }
        if (networkAirData != null) {
            airDataDao.insertAirData(
                networkAirData.mapToAirDataEntity(station)
            )
        } else {
            airDataDao.insertAirData(
                AirDataEntity(
                    station = station,
                    date = null,
                    airLevel = AirLevel.LevelError,
                    detailAirDataEntity = DetailAirDataEntity(
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
                    dailyForecastListEntity = arrayListOf(),
                    information = null,
                    koreaForecastMapImgUrl = null,
                    koreaModelGif = KoreaForecastModelGifEntity(
                        pm10GifUrl = null,
                        pm25GifUrl = null
                    )
                )
            )
        }
    }
}