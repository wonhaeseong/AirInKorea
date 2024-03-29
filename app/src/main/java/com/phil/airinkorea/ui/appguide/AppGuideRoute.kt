package com.phil.airinkorea.ui.appguide

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.viewmodel.AppGuideUiState
import com.phil.airinkorea.viewmodel.AppGuideViewModel

data class AppGuideContent(
    @StringRes val title: Int,
    val content: @Composable () -> Unit,
)

@Composable
fun AppGuideRoute(
    appGuideViewModel: AppGuideViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val appGuideUiState: AppGuideUiState by appGuideViewModel.appGuideUiState.collectAsStateWithLifecycle()
    val appGuideContentList = listOf(
        AppGuideContent(R.string.cautions) {
            GuideContentCommon(
                text = appGuideUiState.appGuide?.cautionGuideText ?: ""
            )
        },
        AppGuideContent(R.string.data_loading) {
            GuideContentCommon(
                text = appGuideUiState.appGuide?.dataLoadingGuideText ?: ""
            )
        },
        AppGuideContent(R.string.air_pollution_level) {
            GuideContentAirPollutionLevel(
                text = appGuideUiState.appGuide?.airPollutionLevelGuideText ?: ""
            )
        },
        AppGuideContent(R.string.details) {
            GuideContentCommon(
                text = appGuideUiState.appGuide?.detailGuideText ?: ""
            )
        },
        AppGuideContent(R.string.information) {
            GuideContentCommon(
                text = appGuideUiState.appGuide?.informationGuideText ?: ""
            )
        },
        AppGuideContent(R.string.dailyForecast) {
            GuideContentCommon(
                text = appGuideUiState.appGuide?.dailyForecastGuideText ?: ""
            )
        },
        AppGuideContent(R.string.koreaForecastMap) {
            GuideContentCommon(
                text = appGuideUiState.appGuide?.koreaForecastModelGuideText ?: ""
            )
        },
        AppGuideContent(R.string.add_location) {
            GuideContentCommon(
                text = appGuideUiState.appGuide?.addLocationGuideText ?: ""
            )
        }
    )

    AppGuideScreen(
        appGuideContentList = appGuideContentList,
        onBackButtonClick = onBackButtonClick
    )
}

private val contentPadding = 10.dp

@Composable
fun GuideContentCommon(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = contentPadding)
    ) {
        Text(
            text = text,
            style = AIKTheme.typography.body1,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun GuideContentAirPollutionLevel(
    modifier: Modifier = Modifier,
    text: String
) {
    val pollutionImgList = arrayListOf<@receiver:DrawableRes Int>(
        R.drawable.pm10,
        R.drawable.pm25,
        R.drawable.no2,
        R.drawable.so2,
        R.drawable.co,
        R.drawable.o3
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = contentPadding)
    ) {
        Text(
            text = text,
            style = AIKTheme.typography.body1,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        for (img in pollutionImgList) {
            Row(
                modifier = modifier
                    .wrapContentSize()
                    .padding(vertical = 10.dp)
                    .horizontalScroll(state = rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = img),
                    contentDescription = null
                )
            }
        }
    }
}