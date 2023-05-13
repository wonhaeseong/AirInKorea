package com.phil.airinkorea.data.repository

import com.phil.airinkorea.datastore.AIKDataStore
import com.phil.airinkorea.domain.model.Mode
import com.phil.airinkorea.domain.repository.AppStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppStatusRepositoryImpl @Inject constructor(
    private val aikDataStore: AIKDataStore,
) : AppStatusRepository {
    override fun getDefaultMode(): Flow<Mode> =
        aikDataStore.getDefaultMode()


    override suspend fun fetchDefaultMode(mode: Mode) {
        aikDataStore.fetchDefaultMode(mode = mode)
    }
}