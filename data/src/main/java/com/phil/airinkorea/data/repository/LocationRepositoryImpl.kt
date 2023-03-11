package com.phil.airinkorea.data.repository

import android.util.Log
import com.phil.airinkorea.database.dao.AddressDao
import com.phil.airinkorea.database.model.LocationEntity
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.repository.LocationRepository
import javax.inject.Inject
import com.phil.airinkorea.domain.model.Result

class LocationRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao
) : LocationRepository {

    override suspend fun getSearchResult(query: String?): List<Location> =
        addressDao.getAddresses(query).mapToLocation()

}

private fun List<LocationEntity>.mapToLocation(): List<Location> =
    this.map { entity ->
        Location(
            `do` = entity.enDo,
            sigungu = entity.enSigungu,
            eupmyeondong = entity.enEupmyeondong
        )
    }