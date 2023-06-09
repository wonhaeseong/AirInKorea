package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.datastore.AIKDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppStatusRepositoryImpl @Inject constructor(
    private val aikDataStore: AIKDataStore,
) : AppStatusRepository {
    override fun getDefaultPage(): Flow<Int> =
        aikDataStore.getDefaultPage()


    override suspend fun fetchDefaultPage(page: Int) {
        aikDataStore.fetchDefaultPage(pageNum = page)
    }
}