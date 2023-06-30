package com.phil.airinkorea.ui.appinfo

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.phil.airinkorea.R
import com.phil.airinkorea.data.model.DeveloperInfo
import com.phil.airinkorea.ui.theme.icon.AIKIcons
import com.phil.airinkorea.ui.viewmodel.AppInfoUiState
import com.phil.airinkorea.ui.viewmodel.AppInfoViewModel

data class AppInfoContent(
    @StringRes val title: Int,
    val onClick: () -> Unit = {},
    val content: @Composable () -> Unit,
)

@Composable
fun AppInfoRoute(
    appInfoViewModel: AppInfoViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val appInfoUiState by appInfoViewModel.appInfoUiState
    when (appInfoUiState) {
        AppInfoUiState.Loading -> Unit
        is AppInfoUiState.Success -> {
            val appInfoContentList = listOf(
                AppInfoContent(
                    title = R.string.open_source_licenses,
                    onClick = { appInfoViewModel.showOpenSourceLicenses() }) {},
                AppInfoContent(
                    title = R.string.developer_info,
                ) {
                    InfoContentDeveloperInfo(
                        developerInfo = (appInfoUiState as AppInfoUiState.Success).developerInfo,
                        onGithubIconClick = {appInfoViewModel.showGithubInBrowser(it)}
                    )
                }
            )
            AppInfoScreen(
                onBackButtonClick = onBackButtonClick,
                appInfoContentList = appInfoContentList
            )
        }
    }
}

@Composable
fun InfoContentDeveloperInfo(
    modifier: Modifier = Modifier,
    developerInfo: DeveloperInfo,
    onGithubIconClick: (Uri) -> Unit
) {
    val myFaceImg: Int = R.drawable.myface
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = myFaceImg),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(shape = CircleShape)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Column {
            //Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = developerInfo.lastName, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = developerInfo.firstName, style = MaterialTheme.typography.subtitle1)
            }
            Spacer(modifier = Modifier.size(5.dp))
            //국적
            Text(text = developerInfo.nationality, style = MaterialTheme.typography.subtitle1)
            //github
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onGithubIconClick(developerInfo.github) }) {
                    Icon(painter = painterResource(id = AIKIcons.github), contentDescription = null)
                }
            }

        }
    }
}

@Preview
@Composable
fun InfoContentDeveloperInfoPreview() {
    val developerInfo = DeveloperInfo(
        firstName = "Won",
        lastName = "HaeSeong",
        github = Uri.parse("https://github.com/want8607"),
        nationality = "South Korea"
    )
    InfoContentDeveloperInfo(developerInfo = developerInfo, onGithubIconClick = {})
}