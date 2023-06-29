package com.phil.airinkorea.ui.appguide

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.ui.commoncomponent.CommonExpendableContent
import com.phil.airinkorea.ui.commoncomponent.CommonTopAppBar
import com.phil.airinkorea.ui.modifier.bottomBorder
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.common_background
import com.phil.airinkorea.ui.theme.divider


@Composable
fun AppGuideScreen(
    onBackButtonClick: () -> Unit,
    appGuideContentList: List<AppGuideContent>
) {
    val scrollState = rememberScrollState()
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
                .verticalScroll(scrollState)
        ) {
            for (content in appGuideContentList) {
                CommonExpendableContent(
                    title = stringResource(id = content.title),
                    modifier = Modifier
                        .bottomBorder(
                            1.dp,
                            divider
                        )
                        .fillMaxWidth()
                ) {
                    content.content()
                }
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
        AppGuideContent(R.string.koreaForecastMap) {},
        AppGuideContent(R.string.add_location){

        }
    )
    AIKTheme(airLevel = AirLevel.Level1) {
        AppGuideScreen(
            appGuideContentList = appGuideContentList,
            onBackButtonClick = {}
        )
    }
}
