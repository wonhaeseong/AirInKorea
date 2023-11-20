package com.phil.airinkorea.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.UserLocation
import com.phil.airinkorea.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageLocationUiState(
    var bookmark: UserLocation? = null,
    var userLocationList: List<UserLocation> = emptyList()
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

    fun deleteLocation(userLocation: UserLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.deleteUserLocation(userLocation)
            if (locationRepository.getSelectedLocationStream().first() == null){
                locationRepository.selectBookmark()
            }
        }
    }

    fun updateBookmark(userLocation: UserLocation) {
        viewModelScope.launch(Dispatchers.IO) {
                manageLocationUiState.value.bookmark?.let {
                    locationRepository.updateBookmark(
                        oldBookmark = it,
                        newBookmark = userLocation
                    )
                }
        }
    }
}