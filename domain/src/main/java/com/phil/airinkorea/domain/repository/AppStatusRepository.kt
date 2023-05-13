package com.phil.airinkorea.domain.repository

import com.phil.airinkorea.domain.model.Mode
import kotlinx.coroutines.flow.Flow

interface AppStatusRepository {
    fun getDefaultMode(): Flow<Mode>
    suspend fun fetchDefaultMode(mode: Mode)
}