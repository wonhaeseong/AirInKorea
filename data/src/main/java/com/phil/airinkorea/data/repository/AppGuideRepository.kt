package com.phil.airinkorea.data.repository

import com.phil.airinkorea.data.model.AppGuide

interface AppGuideRepository {
    fun getAppGuideData(): AppGuide
}