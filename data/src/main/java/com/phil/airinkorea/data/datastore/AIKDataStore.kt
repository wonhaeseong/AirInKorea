package com.phil.airinkorea.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val DATASTORE_NAME = "AIKDataStore"

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class AIKDataStore @Inject constructor(
    private val context: Context
) {

    companion object {
        val page = intPreferencesKey("PAGE")
    }

    fun getDefaultPage(): Flow<Int> = context.datastore.data.map { value ->
        value[page] ?: 0
    }


    suspend fun fetchDefaultPage(pageNum: Int) {
        context.datastore.edit { value ->
            value[page] = pageNum
        }
    }
}