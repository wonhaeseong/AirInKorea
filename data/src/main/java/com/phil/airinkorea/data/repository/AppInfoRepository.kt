package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.DeveloperInfo

interface AppInfoRepository {
    fun getDeveloperInfo(): DeveloperInfo
}