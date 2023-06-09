package com.phil.airinkorea.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddLocationUiState(
    var searchResult: List<Location> = emptyList()
)

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _searchResult = MutableStateFlow(AddLocationUiState())
    val searchResult: StateFlow<AddLocationUiState> = _searchResult
    fun getSearchResult(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.getSearchResult(query).collect { newResult ->
                _searchResult.update {
                    it.copy(
                        searchResult = newResult
                    )
                }
            }
            Log.d("searchResult", searchResult.value.toString())
        }
    }

    fun addUserLocation(location: Location){
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.addUserLocation(location)
        }
    }
}