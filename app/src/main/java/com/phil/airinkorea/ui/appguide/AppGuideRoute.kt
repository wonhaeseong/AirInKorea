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
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.theme.AIKTypography
import com.phil.airinkorea.ui.viewmodel.AppGuideUiState
import com.phil.airinkorea.ui.viewmodel.AppGuideViewModel

data class AppGuideContent(
    @StringRes val title: Int,
    val content: @Composable () -> Unit,
)

@Composable
fun AppGuideRoute(
    appGuideViewModel: AppGuideViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val appGuideUiState by appGuideViewModel.appGuideUiState
    when (appGuideUiState) {
        AppGuideUiState.Loading -> Unit
        is AppGuideUiState.Success -> {
            val successAppGuideUiState = (appGuideUiState as AppGuideUiState.Success)
            val appGuideContentList = listOf(
                AppGuideContent(R.string.cautions) { GuideContentCommon(text = successAppGuideUiState.appGuide.cautionGuideText) },
                AppGuideContent(R.string.data_loading) { GuideContentCommon(text = successAppGuideUiState.appGuide.dataLoadingGuideText) },
                AppGuideContent(R.string.air_pollution_level) { GuideContentAirPollutionLevel(text = successAppGuideUiState.appGuide.airPollutionLevelGuideText) },
                AppGuideContent(R.string.details) { GuideContentCommon(text = successAppGuideUiState.appGuide.detailGuideText) },
                AppGuideContent(R.string.information) { GuideContentCommon(text =successAppGuideUiState.appGuide.informationGuideText )},
                AppGuideContent(R.string.dailyForecast) { GuideContentCommon(text =successAppGuideUiState.appGuide.dailyForecastGuideText )},
                AppGuideContent(R.string.koreaForecastMap) { GuideContentCommon(text =successAppGuideUiState.appGuide.koreaForecastModelGuideText )},
                AppGuideContent(R.string.add_location) { GuideContentCommon(text =successAppGuideUiState.appGuide.addLocationGuideText )}
            )

            AppGuideScreen(
                appGuideContentList = appGuideContentList,
                onBackButtonClick = onBackButtonClick
            )
        }
    }
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
            style = AIKTypography.body1,
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
            style = AIKTypography.body1,
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