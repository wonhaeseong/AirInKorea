package com.phil.airinkorea.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.phil.airinkorea.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *  @param bookmarkedLocation 사용자가 북마크한 위치
 *  @param userLocationList 사용자가 저장한 위치들의 목록
 */
sealed class DrawerUiState {
    data class Success(
        val gps: Location? = null,
        val bookmark: Location? = null,
        val userLocationList: List<Location> = emptyList(),
    ) : DrawerUiState()
    object Failure: DrawerUiState()
    object Loading: DrawerUiState()
}

@HiltViewModel
class DrawerViewModel @Inject constructor() : ViewModel() {

}