package com.phil.airinkorea.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phil.airinkorea.ui.viewmodel.DrawerUiState
import com.phil.airinkorea.ui.viewmodel.DrawerViewModel
import com.phil.airinkorea.ui.viewmodel.HomeUiState
import com.phil.airinkorea.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeRoute(
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    drawerViewModel: DrawerViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val homeUiState : HomeUiState by homeViewModel.airData.collectAsStateWithLifecycle()
    HomeScreen(
        onRefresh = {homeViewModel.fetchAirData()},
        onManageLocationClick = onManageLocationClick,
        onParticulateMatterInfoClick = onParticulateMatterInfoClick,
        onAppInfoClick = onAppInfoClick,
        homeUiState = homeUiState,
        drawerUiState = DrawerUiState()
    )
}