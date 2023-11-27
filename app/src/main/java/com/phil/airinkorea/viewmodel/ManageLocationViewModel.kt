package com.phil.airinkorea.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageLocationUiState(
    var bookmark: Location? = null,
    var userLocationList: List<Location> = emptyList()
)

@HiltViewModel
class ManageLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    val manageLocationUiState: StateFlow<ManageLocationUiState> =
        combine(
            locationRepository.getBookmarkStream(),
            locationRepository.getCustomLocationListStream()
        ) { bookmark, userLocationList ->
            ManageLocationUiState(
                bookmark = bookmark,
                userLocationList = userLocationList
            )
        }.flowOn(Dispatchers.IO).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ManageLocationUiState()
        )

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.deleteCustomLocation(location)
        }
    }

    fun updateBookmark(location: Location) {
        viewModelScope.launch {
                manageLocationUiState.value.bookmark?.let {oldBookmark ->
                    locationRepository.updateBookmark(
                        oldBookmark = oldBookmark,
                        newBookmark = location
                    )
                }
        }
    }
}