package com.phil.airinkorea.ui.appinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.phil.airinkorea.ui.composableActivityViewModel
import com.phil.airinkorea.ui.viewmodel.AppInfoViewModel

@Composable
fun AppInfoRoute(
    appInfoViewModel: AppInfoViewModel = composableActivityViewModel(),
    onBackButtonClick: () -> Unit
) {
    val appInfoUiState by appInfoViewModel.appInfoUiState
    AppInfoScreen(
        onBackButtonClick = onBackButtonClick,
        onOpensourceLicensesClick = {appInfoViewModel.showOpenSourceLicenses()},
        onGithubIconClick = {appInfoViewModel.showGithubInBrowser(it)},
        appInfoUiState = appInfoUiState
    )
}