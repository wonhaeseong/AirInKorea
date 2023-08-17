package com.phil.airinkorea.ui.appinfo

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.phil.airinkorea.viewmodel.AppInfoViewModel

@Composable
fun AppInfoRoute(
    appInfoViewModel: AppInfoViewModel = hiltViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    onBackButtonClick: () -> Unit
) {
    val appInfoUiState by appInfoViewModel.appInfoUiState
    AppInfoScreen(
        onBackButtonClick = onBackButtonClick,
        onOpensourceLicensesClick = { appInfoViewModel.showOpenSourceLicenses() },
        onGithubIconClick = { appInfoViewModel.showGithubInBrowser(it) },
        appInfoUiState = appInfoUiState
    )
}