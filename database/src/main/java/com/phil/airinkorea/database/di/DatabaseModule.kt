package com.phil.airinkorea.database.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import com.phil.airinkorea.database.AIKDatabase
import com.phil.airinkorea.database.dao.AddressDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Singleton
import kotlin.concurrent.thread

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideAddressDao(aIKDatabase: AIKDatabase): AddressDao = aIKDatabase.addressDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AIKDatabase =
        Room.databaseBuilder(
            appContext, AIKDatabase::class.java,
            "locations.db"
        ).createFromAsset("database/locations.db")
            .build()
}