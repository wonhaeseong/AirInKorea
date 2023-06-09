package com.phil.airinkorea

import com.phil.airinkorea.data.repository.AppStatusRepository
import kotlinx.coroutines.flow.Flow

class TestAppStatusRepository : AppStatusRepository{
    override fun getDefaultPage(): Flow<Int> {
        TODO("Not yet implemented")
    }


    override suspend fun fetchDefaultPage(page: Int) {
        TODO("Not yet implemented")
    }
}