package com.phil.airinkorea.data.network.di

import com.phil.airinkorea.data.network.firebase.FirebaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideFirebaseClient(): FirebaseClient = FirebaseClient()
}