package com.phil.airinkorea.datastore.di

import android.content.Context
import com.phil.airinkorea.datastore.AIKDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): AIKDataStore = AIKDataStore(context)
}