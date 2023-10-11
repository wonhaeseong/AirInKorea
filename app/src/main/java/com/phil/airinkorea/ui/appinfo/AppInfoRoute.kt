package com.phil.airinkorea.ui.appinfo

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.phil.airinkorea.R
import com.phil.airinkorea.viewmodel.AppInfoViewModel

@Composable
fun AppInfoRoute(
    appInfoViewModel: AppInfoViewModel = hiltViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    onBackButtonClick: () -> Unit
) {
    val context = LocalContext.current
    val appInfoUiState by appInfoViewModel.appInfoUiState
    AppInfoScreen(
        onBackButtonClick = onBackButtonClick,
        onOpensourceLicensesClick = {
            context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            OssLicensesMenuActivity.setActivityTitle(context.getString(R.string.open_source_licenses))
        },
        onGithubIconClick = { uri ->
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        },
        appInfoUiState = appInfoUiState
    )
}