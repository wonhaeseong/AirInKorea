package com.phil.airinkorea.ui.appguide

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.ui.commoncomponent.CommonContentTitle
import com.phil.airinkorea.ui.commoncomponent.CommonTopAppBar
import com.phil.airinkorea.ui.modifier.bottomBorder
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.common_background
import com.phil.airinkorea.ui.theme.divider

data class AppGuideContent(
    @StringRes val title: Int,
    val onClick: () -> Unit
)

@Composable
fun AppGuideScreen(
    appGuideContentList: List<AppGuideContent>,
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(
                onBackButtonClick = onBackButtonClick,
                title = stringResource(id = R.string.app_guide),
                modifier = Modifier.statusBarsPadding()
            )
        },
        backgroundColor = common_background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            for (content in appGuideContentList) {
                CommonContentTitle(
                    title = stringResource(id = content.title),
                    modifier = Modifier
                        .bottomBorder(
                            1.dp,
                            divider
                        )
                        .fillMaxWidth()
                ) { content.onClick }
            }
        }
    }
}


@Preview
@Composable
fun AppGuideScreenPreview() {
    val appGuideContentList = listOf(
        AppGuideContent(R.string.data_loading) {},
        AppGuideContent(R.string.air_pollution_level) {},
        AppGuideContent(R.string.details) {},
        AppGuideContent(R.string.information) {},
        AppGuideContent(R.string.dailyForecast) {},
        AppGuideContent(R.string.koreaForecastMap) {}
    )
    AIKTheme(airLevel = AirLevel.Level1) {
        AppGuideScreen(
            appGuideContentList = appGuideContentList
        ) {}
    }
}
