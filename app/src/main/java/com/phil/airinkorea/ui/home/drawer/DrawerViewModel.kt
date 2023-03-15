package com.phil.airinkorea.ui.home.drawer

import com.phil.airinkorea.domain.model.Location

/**
*  @param bookmarkedLocation 사용자가 북마크한 위치
*  @param userLocationList 사용자가 저장한 위치들의 목록
*/
data class DrawerUiState(
    val bookmarkedLocation: String,
    val userLocationList: List<String> = emptyList(),
)