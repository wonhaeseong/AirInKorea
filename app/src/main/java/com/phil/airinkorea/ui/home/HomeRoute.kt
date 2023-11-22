package com.phil.airinkorea.ui.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phil.airinkorea.viewmodel.DrawerUiState
import com.phil.airinkorea.viewmodel.HomeUiState
import com.phil.airinkorea.viewmodel.HomeViewModel

@Composable
fun HomeRoute(
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    val context = LocalContext.current
    val packageName = context.packageName
    val homeUiState: HomeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    val drawerUiState: DrawerUiState by homeViewModel.drawerUiState.collectAsStateWithLifecycle()
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            homeViewModel.onLocationPermissionGranted()
        } else {
            homeViewModel.onLocationPermissionDenied()
        }
    }
    val enableLocationSettingLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                homeViewModel.onLocationSettingGranted()
            } else {
                homeViewModel.onLocationSettingDenied()
            }
        }

    HomeScreen(
        onRefresh = { homeViewModel.onRefreshHomeScreen() },
        onSettingButtonClick = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", packageName, null)
            context.startActivity(intent)
        },
        onManageLocationClick = onManageLocationClick,
        onParticulateMatterInfoClick = onParticulateMatterInfoClick,
        onAppInfoClick = onAppInfoClick,
        onDrawerGPSClick = { homeViewModel.onDrawerGPSClick() },
        onDrawerBookmarkClick = { homeViewModel.onDrawerBookmarkClick() },
        onDrawerCustomLocationClick = { homeViewModel.onDrawerCustomLocationClick(it) },
        homeUiState = homeUiState,
        drawerUiState = drawerUiState
    )

    LaunchedEffect(homeUiState.requestLocationPermission) {
        if (homeUiState.requestLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    LaunchedEffect(homeUiState.resolvableApiException) {
        if (homeUiState.resolvableApiException != null) {
            val intentSenderRequest =
                IntentSenderRequest.Builder(homeUiState.resolvableApiException!!.resolution)
                    .build()
            enableLocationSettingLauncher.launch(intentSenderRequest)
        }
    }
}
