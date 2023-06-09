package com.phil.airinkorea.data.repository

import kotlinx.coroutines.flow.Flow

interface AppStatusRepository {
    /**
     * 0 - GPS
     * 1 - Bookmark
     * 2~ - 일반 Location
     */
    fun getDefaultPage(): Flow<Int>
    suspend fun fetchDefaultPage(page: Int)
}