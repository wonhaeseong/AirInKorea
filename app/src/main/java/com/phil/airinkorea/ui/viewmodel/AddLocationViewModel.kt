package com.phil.airinkorea.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.domain.usecases.search.GetSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddLocationUiState(
    var searchResult: List<Location> = emptyList()
)

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase
) : ViewModel() {
    private val _searchResult = MutableStateFlow<List<Location>>(emptyList())
    val searchResult: StateFlow<List<Location>> = _searchResult.asStateFlow()
    suspend fun getSearchResult(query: String?) {
        viewModelScope.launch {
            _searchResult.update {
                TODO()
//                getSearchResultUseCase(query)
            }
            Log.d("searchResult",searchResult.value.toString())
        }
    }
}