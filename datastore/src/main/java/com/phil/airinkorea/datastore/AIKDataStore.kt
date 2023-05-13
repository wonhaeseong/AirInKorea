package com.phil.airinkorea.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.phil.airinkorea.domain.model.Mode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val DATASTORE_NAME = "AIKDataStore"

class AIKDataStore @Inject constructor(
    private val context: Context
) {
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    companion object {
        val isGPSMode = booleanPreferencesKey("MODE")
    }

    fun getDefaultMode(): Flow<Mode> = context.datastore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { value ->
        when (value[isGPSMode]) {
            true -> {
                Mode.GPS
            }

            false -> {
                Mode.Bookmark
            }

            else -> {
                Mode.GPS
            }
        }
    }

    suspend fun fetchDefaultMode(mode: Mode) {
        context.datastore.edit { value ->
            when (mode) {
                Mode.GPS -> {
                    value[isGPSMode] = true
                }

                Mode.Bookmark -> {
                    value[isGPSMode] = false
                }
            }
        }
    }
}