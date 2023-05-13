package com.phil.airinkorea.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phil.airinkorea.MainActivity
import com.phil.airinkorea.findActivity
import com.phil.airinkorea.ui.viewmodel.HomeUiState
import com.phil.airinkorea.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeRoute(
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val mainActivity = LocalContext.current.findActivity() as MainActivity
    val homeUiState: HomeUiState by homeViewModel.airData.collectAsStateWithLifecycle()
    if (homeUiState.isGPS) {

    }

    HomeScreen(
        onRefresh = {
            if (!mainActivity.checkLocationPermission() && homeUiState.isGPS && !mainActivity.checkLocationPermissionPhone())
            homeViewModel.fetchAirData()
        },
        onManageLocationClick = onManageLocationClick,
        onParticulateMatterInfoClick = onParticulateMatterInfoClick,
        onAppInfoClick = onAppInfoClick,
        homeUiState = homeUiState
    )
}










