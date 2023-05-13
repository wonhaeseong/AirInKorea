package com.phil.airinkorea.data.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.phil.airinkorea.data.repository.AirDataRepositoryImpl
import com.phil.airinkorea.data.repository.AppStatusRepositoryImpl
import com.phil.airinkorea.data.repository.LocationRepositoryImpl
import com.phil.airinkorea.database.dao.GPSLocationDao
import com.phil.airinkorea.database.dao.LocationDao
import com.phil.airinkorea.database.dao.UserLocationsDao
import com.phil.airinkorea.domain.repository.AirDataRepository
import com.phil.airinkorea.domain.repository.AppStatusRepository
import com.phil.airinkorea.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideLocationRepository(
        locationDao: LocationDao,
        gpsLocationDao: GPSLocationDao,
        userLocationsDao: UserLocationsDao,
        application: Application
    ): LocationRepository = LocationRepositoryImpl(
        locationDao = locationDao,
        gpsLocationDao = gpsLocationDao,
        userLocationsDao = userLocationsDao,
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application),
        application = application
    )

    @Provides
    fun provideAirDataRepository(
        airDataRepository: AirDataRepositoryImpl
    ): AirDataRepository = airDataRepository

    @Provides
    fun provideAppStatusRepository(
        appStatusRepository: AppStatusRepositoryImpl
    ): AppStatusRepository = appStatusRepository
}

