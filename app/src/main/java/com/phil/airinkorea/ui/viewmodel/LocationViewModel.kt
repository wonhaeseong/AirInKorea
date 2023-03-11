package com.phil.airinkorea.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.usecases.GetSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase
) : ViewModel() {
    private val _searchResult = MutableStateFlow<List<Location>>(emptyList())
    val searchResult: StateFlow<List<Location>> = _searchResult.asStateFlow()
    suspend fun getSearchResult(query: String?) {
        viewModelScope.launch {
            _searchResult.update {
                getSearchResultUseCase(query)
            }
            Log.d("searchResult",searchResult.value.toString())
        }
    }
}