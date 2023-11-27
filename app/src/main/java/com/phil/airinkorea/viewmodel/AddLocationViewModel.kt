package com.phil.airinkorea.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddLocationUiState(
    var searchResult: List<Location> = emptyList()
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val addLocationUiState: StateFlow<AddLocationUiState> = _searchText.flatMapLatest { searchText ->
        locationRepository.getSearchResult(searchText)
    }.map { searchResult ->
        AddLocationUiState(searchResult)
    }.stateIn(
        viewModelScope, started = SharingStarted.WhileSubscribed(5000),
        initialValue = AddLocationUiState()
    )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun addUserLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.addUserLocation(location)
        }
    }
}