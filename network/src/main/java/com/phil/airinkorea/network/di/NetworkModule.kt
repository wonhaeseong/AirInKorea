package com.phil.airinkorea.network.di

import com.phil.airinkorea.network.firebase.FirebaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideFirebaseClient():FirebaseClient = FirebaseClient()

}