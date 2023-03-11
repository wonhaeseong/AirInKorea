package com.phil.airinkorea.ui.addlocation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.ui.viewmodel.LocationViewModel

@Composable
fun AddLocationRoute(
    locationViewModel: LocationViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val searchResultState: List<Location> by locationViewModel.searchResult.collectAsStateWithLifecycle()
    AddLocationScreen(
        searchResultState = searchResultState,
        onBackButtonClick = onBackButtonClick,
        onSearchTextChange = {
            locationViewModel.getSearchResult(it.text)
        },
        onDialogConfirmButtonClick = { TODO() }
    )
}