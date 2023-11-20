package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.AppGuide
import kotlinx.coroutines.flow.Flow

interface AppGuideRepository {
    fun getAppGuideDataStream(): Flow<AppGuide>
}