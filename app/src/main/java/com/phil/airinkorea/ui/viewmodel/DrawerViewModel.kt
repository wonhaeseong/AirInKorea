package com.phil.airinkorea.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.phil.airinkorea.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *  @param bookmarkedLocation 사용자가 북마크한 위치
 *  @param userLocationList 사용자가 저장한 위치들의 목록
 */
data class DrawerUiState(
    var gps: Location? = null,
    var bookmark: Location? = null,
    var userLocationList: List<Location> = emptyList(),
    var isLoading: Boolean = true
)

@HiltViewModel
class DrawerViewModel @Inject constructor(

) : ViewModel() {

}