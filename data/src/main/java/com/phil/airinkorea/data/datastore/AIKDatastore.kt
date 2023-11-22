package com.phil.airinkorea.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.phil.airinkorea.data.LocalPageStore
import com.phil.airinkorea.data.model.Page
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val DATASTORE_NAME = "AIKDataStore"
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class AIKDatastore @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalPageStore {
    companion object {
        val pageKey = intPreferencesKey("PAGE")
    }

    override fun getCurrentPageStream() = context.datastore.data.map { value ->
        when (val page = value[pageKey]) {
            null, 0 -> Page.GPS
            1 -> Page.Bookmark
            else -> Page.CustomLocation(page)
        }
    }

    override suspend fun updatePage(newPage: Page) {
        context.datastore.edit { value ->
            value[pageKey] = when (newPage) {
                Page.GPS -> 0
                Page.Bookmark -> 1
                is Page.CustomLocation -> newPage.pageNum
            }
        }
    }
}