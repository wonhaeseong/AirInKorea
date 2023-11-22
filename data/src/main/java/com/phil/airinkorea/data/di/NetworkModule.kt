package com.phil.airinkorea.data.di

import com.phil.airinkorea.data.NetworkDataSource
import com.phil.airinkorea.data.network.firebase.FirebaseClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun provideNetworkDataSource(firebaseClient: FirebaseClient): NetworkDataSource
}