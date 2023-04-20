package com.phil.airinkorea.data.di

import com.phil.airinkorea.data.repository.AirDataRepositoryImpl
import com.phil.airinkorea.data.repository.LocationRepositoryImpl
import com.phil.airinkorea.domain.repository.AirDataRepository
import com.phil.airinkorea.domain.repository.LocationRepository
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
}

