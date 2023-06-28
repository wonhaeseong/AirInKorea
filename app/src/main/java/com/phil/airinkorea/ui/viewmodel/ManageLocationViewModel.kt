package com.phil.airinkorea.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.AppStatusRepository
import com.phil.airinkorea.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface ManageLocationUiState {
    object Loading : ManageLocationUiState
    data class Success(
        var bookmark: Location? = null,
        var userLocationList: List<Location> = emptyList()
    ) : ManageLocationUiState
}

@HiltViewModel
class ManageLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val appStatusRepository: AppStatusRepository
) : ViewModel() {
    val manageLocationUiState: StateFlow<ManageLocationUiState> =
        combine(
            locationRepository.getBookmark(),
            locationRepository.getUserLocationList()
        ) { bookmark, userLocationList ->
            ManageLocationUiState.Success(
                bookmark = bookmark,
                userLocationList = userLocationList
            )
        }.flowOn(Dispatchers.IO).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ManageLocationUiState.Loading
        )

    fun deleteLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.deleteUserLocation(location)
        }
    }

    fun updateBookmark(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO){
              locationRepository.updateBookmark(
                oldBookmark = locationRepository.getBookmark().first(),
                newBookmark = location
              )
            }
            appStatusRepository.fetchDefaultPage(1)
        }
    }
}