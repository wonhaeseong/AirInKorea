package com.phil.airinkorea.ui.home

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phil.airinkorea.ui.viewmodel.DrawerUiState
import com.phil.airinkorea.ui.viewmodel.HomeUiState
import com.phil.airinkorea.ui.viewmodel.HomeViewModel

@Composable
fun HomeRoute(
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    val drawerUiState: DrawerUiState by homeViewModel.drawerUiState.collectAsStateWithLifecycle()

    HomeScreen(
        onRefresh = { homeViewModel.onRefreshHomeScreen() },
        onManageLocationClick = onManageLocationClick,
        onParticulateMatterInfoClick = onParticulateMatterInfoClick,
        onAppInfoClick = onAppInfoClick,
        onDrawerLocationClick = { homeViewModel.onDrawerLocationClick(it) },
        homeUiState = homeUiState,
        drawerUiState = drawerUiState
    )
}










