package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.mapToAirDataEntity
import com.phil.airinkorea.database.dao.AirDataDao
import com.phil.airinkorea.database.model.AirDataEntity
import com.phil.airinkorea.database.model.DetailAirDataEntity
import com.phil.airinkorea.database.model.KoreaForecastModelGifEntity
import com.phil.airinkorea.database.model.mapToExternalModel
import com.phil.airinkorea.domain.model.AirData
import com.phil.airinkorea.domain.model.AirLevel
import com.phil.airinkorea.domain.model.KoreaForecastModelGif
import com.phil.airinkorea.domain.repository.AirDataRepository
import com.phil.airinkorea.network.firebase.FirebaseClient
import com.phil.airinkorea.network.model.NetworkAirData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AirDataRepositoryImpl @Inject constructor(
    private val airDataDao: AirDataDao,
    private val firebaseClient: FirebaseClient
) : AirDataRepository {

    override fun getAirData(station: String): Flow<AirData> =

        airDataDao.getAirData(station).map { it.mapToExternalModel() }

    override suspend fun fetchAirData(station: String) {
        fetchDatabase(station, firebaseClient.getAirData(station))
    }

    override suspend fun getKoreaForecastModelGif(station: String): KoreaForecastModelGif =
        airDataDao.getKoreaForecastModelGif(station)

    private suspend fun fetchDatabase(station: String, networkAirData: NetworkAirData?) {
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