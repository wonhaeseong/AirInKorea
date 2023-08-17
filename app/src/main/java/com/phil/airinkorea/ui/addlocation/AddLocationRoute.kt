package com.phil.airinkorea.ui.addlocation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phil.airinkorea.viewmodel.AddLocationUiState
import com.phil.airinkorea.viewmodel.AddLocationViewModel

@Composable
fun AddLocationRoute(
    addLocationViewModel: AddLocationViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val addLocationUiState: AddLocationUiState by addLocationViewModel.searchResult.collectAsStateWithLifecycle()
    AddLocationScreen(
        addLocationUiState = addLocationUiState,
        onBackButtonClick = onBackButtonClick,
        onSearchTextChange = { addLocationViewModel.getSearchResult(it.text) },
        onDialogConfirmButtonClick = {addLocationViewModel.addUserLocation(it)}
    )
}