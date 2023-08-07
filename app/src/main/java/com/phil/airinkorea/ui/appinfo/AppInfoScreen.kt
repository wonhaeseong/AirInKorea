package com.phil.airinkorea.ui.appinfo

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.data.model.DeveloperInfo
import com.phil.airinkorea.ui.commoncomponent.CommonExpendableContent
import com.phil.airinkorea.ui.commoncomponent.CommonTopAppBar
import com.phil.airinkorea.ui.modifier.bottomBorder
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.app_info_core_container
import com.phil.airinkorea.ui.theme.app_info_on_core_container
import com.phil.airinkorea.ui.theme.common_background
import com.phil.airinkorea.ui.theme.divider
import com.phil.airinkorea.ui.theme.icon.AIKIcons
import com.phil.airinkorea.ui.viewmodel.AppInfoUiState

@Composable
fun AppInfoScreen(
    onBackButtonClick: () -> Unit,
    onOpensourceLicensesClick: () -> Unit,
    onGithubIconClick: (Uri) -> Unit,
    appInfoUiState: AppInfoUiState
) {
    when (appInfoUiState) {
        AppInfoUiState.Loading -> Unit
        is AppInfoUiState.Success -> {
            val scrollState = rememberScrollState()
            Scaffold(
                topBar = {
                    CommonTopAppBar(
                        onBackButtonClick = onBackButtonClick,
                        title = stringResource(id = R.string.app_info),
                        modifier = Modifier.statusBarsPadding()
                    )
                },
                backgroundColor = common_background
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    InfoContentOpensourceLicenses(
                        onClick = onOpensourceLicensesClick, modifier = Modifier
                            .bottomBorder(1.dp, divider)
                            .fillMaxWidth()
                    )
                    CommonExpendableContent(
                        title = stringResource(id = R.string.developer_info),
                        modifier = Modifier
                            .bottomBorder(1.dp, divider)
                            .fillMaxWidth()
                    ) {
                        InfoContentDeveloperInfo(
                            developerInfo = appInfoUiState.developerInfo,
                            onGithubIconClick = onGithubIconClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoContentOpensourceLicenses(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.open_source_licenses),
            style = AIKTheme.typography.subtitle1,
            color = app_info_on_core_container
        )
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
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = developerInfo.lastName, style = AIKTheme.typography.subtitle1)
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = developerInfo.firstName, style = AIKTheme.typography.subtitle1)
            }
            Spacer(modifier = Modifier.size(5.dp))
            //국적
            Text(text = developerInfo.nationality, style = AIKTheme.typography.subtitle1)
            Spacer(modifier = Modifier.size(5.dp))
            //이메일
            Text(text = developerInfo.email, style = AIKTheme.typography.subtitle1)
            Spacer(modifier = Modifier.size(5.dp))
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
        email = "gotjd8607@gmail.com",
        github = Uri.parse("https://github.com/want8607"),
        nationality = "South Korea"
    )
    InfoContentDeveloperInfo(developerInfo = developerInfo, onGithubIconClick = {})
}
