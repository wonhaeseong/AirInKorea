package com.phil.airinkorea.data.di

import com.phil.airinkorea.data.repository.AirDataRepository
import com.phil.airinkorea.data.repository.AirDataRepositoryImpl
import com.phil.airinkorea.data.repository.AppGuideRepository
import com.phil.airinkorea.data.repository.AppGuideRepositoryImpl
import com.phil.airinkorea.data.repository.AppInfoRepository
import com.phil.airinkorea.data.repository.AppInfoRepositoryImpl
import com.phil.airinkorea.data.repository.LocationRepository
import com.phil.airinkorea.data.repository.LocationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideLocationRepository(
        locationRepository: LocationRepositoryImpl
    ): LocationRepository = locationRepository

    @Provides
    fun provideAirDataRepository(
        airDataRepository: AirDataRepositoryImpl
    ): AirDataRepository = airDataRepository

    @Provides
    fun provideAppGuideRepository(
        appGuideRepository: AppGuideRepositoryImpl
    ): AppGuideRepository = appGuideRepository

    @Provides
    fun provideAppInfoRepository(
        appInfoRepository: AppInfoRepositoryImpl
    ): AppInfoRepository = appInfoRepository
}

