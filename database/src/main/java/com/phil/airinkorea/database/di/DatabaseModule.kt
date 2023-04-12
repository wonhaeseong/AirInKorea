package com.phil.airinkorea.database.di

import android.content.Context
import androidx.room.Room
import com.phil.airinkorea.database.AIKDatabase
import com.phil.airinkorea.database.dao.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideLocationDao(aIKDatabase: AIKDatabase): LocationDao = aIKDatabase.locationDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AIKDatabase =
        Room.databaseBuilder(
            appContext, AIKDatabase::class.java,
            "locations.db"
        ).createFromAsset("database/locations.db")
            .build()
}