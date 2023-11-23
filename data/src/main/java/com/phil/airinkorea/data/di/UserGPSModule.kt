package com.phil.airinkorea.data.di

import com.phil.airinkorea.data.UserGPSDataSource
import com.phil.airinkorea.data.locationservice.FusedLocationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserGPSModule {
    @Binds
    abstract fun bindLocationManager(locationManagerImpl: FusedLocationProvider): UserGPSDataSource
}