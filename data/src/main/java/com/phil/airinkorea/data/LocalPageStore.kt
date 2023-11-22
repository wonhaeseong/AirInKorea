package com.phil.airinkorea.data

import com.phil.airinkorea.data.model.Page
import kotlinx.coroutines.flow.Flow

interface LocalPageStore {
    fun getCurrentPageStream(): Flow<Page>
    suspend fun updatePage(newPage: Page)
}

