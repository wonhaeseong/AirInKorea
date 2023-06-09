package com.phil.airinkorea.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
    private val locationRepository: LocationRepository
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
        }
    }

    fun updateBookmark(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            //1.bookmark 해제시 기존 location에 추가
            //2.bookmark 밀려났을때
            //3.bookmark 추가
            //4.bookmark 삭제 안댐
        }
    }

}