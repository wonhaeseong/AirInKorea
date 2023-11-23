package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.LocalPageStore
import com.phil.airinkorea.data.database.dao.LocationDao
import com.phil.airinkorea.data.database.dao.UserLocationsDao
import com.phil.airinkorea.data.database.model.mapToExternalModel
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.Page
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

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val userLocationsDao: UserLocationsDao,
    private val localPageStore: LocalPageStore
) : LocationRepository {
    override fun getSearchResult(query: String): Flow<List<Location>> =
        locationDao.searchLocation(query).map { locationList ->
            locationList.map { it.mapToExternalModel() }
        }.flowOn(Dispatchers.IO)

    override fun getCurrentPageStream(): Flow<Page> =
        localPageStore.getCurrentPageStream()

    override fun getCustomLocationListStream(): Flow<List<Location>> =
        userLocationsDao.getCustomLocationListStream().map { locationList ->
            locationList.map { it.mapToExternalModel() }
        }.flowOn(Dispatchers.IO)

    override fun getGPSLocationStream(): Flow<Location?> =
        userLocationsDao.getGPSLocationStream().map { it?.mapToExternalModel() }
            .flowOn(Dispatchers.IO)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSelectedLocationStream(): Flow<Location?> =
        localPageStore.getCurrentPageStream().flatMapLatest { page ->
            when (page) {
                Page.GPS -> getGPSLocationStream()
                Page.Bookmark -> getBookmarkStream()
                is Page.CustomLocation -> getCustomLocationListStream().map { locationList -> locationList[page.pageNum] }
            }
        }


    override fun getBookmarkStream(): Flow<Location> =
        userLocationsDao.getBookmarkStream().map {
            it?.mapToExternalModel() ?: Location(
                `do` = "Seoul",
                sigungu = "Yongsan-gu",
                eupmyeondong = "Namyeong-dong",
                station = "한강대로"
            ).also { defaultLocation ->
                userLocationsDao.insertUserLocation(
                    defaultLocation.mapToUserLocationEntity()
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun deleteCustomLocation(location: Location) {
        withContext(Dispatchers.IO) {
            val currentPage = localPageStore.getCurrentPageStream().first()
            if (currentPage is Page.CustomLocation) {
                val locationIndex =
                    userLocationsDao.getCustomLocationList().map { it.mapToExternalModel() }
                        .indexOf(location)
                if (currentPage.pageNum == locationIndex) {
                    localPageStore.updatePage(Page.Bookmark)
                }
            } else {
                userLocationsDao.deleteUserLocation(location.mapToUserLocationEntity())
            }
        }
    }

    override suspend fun addUserLocation(location: Location) = withContext(Dispatchers.IO) {
        userLocationsDao.insertUserLocation(location.mapToUserLocationEntity())
    }

    override suspend fun updateBookmark(newBookmark: Location, oldBookmark: Location) =
        withContext(Dispatchers.IO) {
            userLocationsDao.updateUserLocation(
                oldBookmark.mapToUserLocationEntity(isBookmark = false)
            )
            userLocationsDao.updateUserLocation(
                newBookmark.mapToUserLocationEntity(isBookmark = true)
            )
        }

    override suspend fun updatePage(newPage: Page) {
        localPageStore.updatePage(newPage)
    }
}
