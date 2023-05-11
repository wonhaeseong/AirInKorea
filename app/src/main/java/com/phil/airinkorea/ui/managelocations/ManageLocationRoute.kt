package com.phil.airinkorea.ui.managelocations

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.phil.airinkorea.ui.viewmodel.ManageLocationUiState
import com.phil.airinkorea.ui.viewmodel.ManageLocationViewModel

@Composable
fun ManageLocationRoute(
    onBackButtonClick: () -> Unit,
    onAddLocationButtonClick: () -> Unit,
    manageLocationViewModel: ManageLocationViewModel = hiltViewModel()
) {
    ManageLocationScreen(
        onAddLocationButtonClick = onAddLocationButtonClick,
        onBackButtonClick = onBackButtonClick,
        onBookmarkButtonClick ={},
        onLocationDeleteButtonClick ={},
        manageLocationUiState = ManageLocationUiState()
    )
}