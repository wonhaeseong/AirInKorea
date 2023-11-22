package com.phil.airinkorea.data.di

import com.phil.airinkorea.data.repository.AirDataRepository
import com.phil.airinkorea.data.repository.AirDataRepositoryImpl
import com.phil.airinkorea.data.repository.AppGuideRepository
import com.phil.airinkorea.data.repository.AppGuideRepositoryImpl
import com.phil.airinkorea.data.repository.AppInfoRepository
import com.phil.airinkorea.data.repository.AppInfoRepositoryImpl
import com.phil.airinkorea.data.repository.LocationRepository
import com.phil.airinkorea.data.repository.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideLocationRepository(
        locationRepository: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    abstract fun provideAirDataRepository(
        airDataRepository: AirDataRepositoryImpl
    ): AirDataRepository

    @Binds
    abstract fun provideAppGuideRepository(
        appGuideRepository: AppGuideRepositoryImpl
    ): AppGuideRepository

    @Binds
    abstract fun provideAppInfoRepository(
        appInfoRepository: AppInfoRepositoryImpl
    ): AppInfoRepository
}

