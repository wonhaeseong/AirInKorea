package com.phil.airinkorea.ui.managelocations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phil.airinkorea.ui.viewmodel.ManageLocationUiState
import com.phil.airinkorea.ui.viewmodel.ManageLocationViewModel

@Composable
fun ManageLocationRoute(
    onBackButtonClick: () -> Unit,
    onAddLocationButtonClick: () -> Unit,
    manageLocationViewModel: ManageLocationViewModel = hiltViewModel()
) {
    val manageLocationUiState: ManageLocationUiState by manageLocationViewModel.manageLocationUiState.collectAsStateWithLifecycle()
    ManageLocationScreen(
        onAddLocationButtonClick = onAddLocationButtonClick,
        onBackButtonClick = onBackButtonClick,
        onBookmarkDialogConfirmButtonClick = { manageLocationViewModel.updateBookmark(it) },
        onDeleteDialogConfirmButtonClick = { manageLocationViewModel.deleteLocation(it) },
        manageLocationUiState = manageLocationUiState
    )
}