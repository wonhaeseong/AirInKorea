package com.phil.airinkorea.data.di

import com.phil.airinkorea.data.datastore.AIKDatastore
import com.phil.airinkorea.data.LocalPageStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalPageModule {
    @Binds
    abstract fun provideDatastore(aikDatastore: AIKDatastore): LocalPageStore
}