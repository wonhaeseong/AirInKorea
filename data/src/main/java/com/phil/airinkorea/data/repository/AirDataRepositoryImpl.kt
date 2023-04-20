package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.mapToAirDataEntity
import com.phil.airinkorea.database.dao.AirDataDao
import com.phil.airinkorea.database.model.mapToExternalModel
import com.phil.airinkorea.domain.model.AirData
import com.phil.airinkorea.domain.model.KoreaForecastModelGif
import com.phil.airinkorea.domain.repository.AirDataRepository
import com.phil.airinkorea.network.firebase.FirebaseClient
import com.phil.airinkorea.network.model.NetworkAirData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AirDataRepositoryImpl @Inject constructor(
    private val airDataDao: AirDataDao
) : AirDataRepository {
    private val firebaseClient = FirebaseClient()

    override fun getAirData(station: String): Flow<AirData> =
        airDataDao.getAirData(station).map { it.mapToExternalModel() }

    override suspend fun fetchAirData(station: String) {
        firebaseClient.getAirData(station)?.let { networkAirData ->
            fetchDatabase(station, networkAirData)
        }
    }

    override suspend fun getKoreaForecastModelGif(station: String): KoreaForecastModelGif =
        airDataDao.getKoreaForecastModelGif(station)

    private suspend fun fetchDatabase(station: String, networkAirData: NetworkAirData) {
        airDataDao.insertAirData(
            networkAirData.mapToAirDataEntity(station)
        )
    }
}