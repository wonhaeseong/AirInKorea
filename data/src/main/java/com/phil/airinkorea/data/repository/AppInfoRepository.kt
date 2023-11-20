package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.DeveloperInfo
import kotlinx.coroutines.flow.Flow

interface AppInfoRepository {
    fun getDeveloperInfoStream(): Flow<DeveloperInfo>
}