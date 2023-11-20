package com.phil.airinkorea.viewmodel

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
    private val _addLocationUiState = MutableStateFlow(AddLocationUiState())
    val addLocationUiState: StateFlow<AddLocationUiState> = _addLocationUiState
    fun getSearchResult(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.getSearchResult(query).collect { newResult ->
                _addLocationUiState.update {
                    it.copy(
                        searchResult = newResult
                    )
                }
            }
            Log.d("searchResult", addLocationUiState.value.toString())
        }
    }

    fun addUserLocation(location: Location){
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.addUserLocation(location)
        }
    }
}