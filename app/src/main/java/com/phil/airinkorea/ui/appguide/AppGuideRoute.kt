package com.phil.airinkorea.ui.appguide

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.AIKTypography
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

    val appGuideContentList = listOf(
        AppGuideContent(R.string.data_loading) { DataLoading(text = "ASDSAD") },
        AppGuideContent(R.string.air_pollution_level) {},
        AppGuideContent(R.string.details) {},
        AppGuideContent(R.string.information) {},
        AppGuideContent(R.string.dailyForecast) {},
        AppGuideContent(R.string.koreaForecastMap) {}
    )

    AppGuideScreen(
        appGuideContentList = appGuideContentList,
        onBackButtonClick = onBackButtonClick
    )
}

private val contentPadding = 10.dp

@Composable
fun DataLoading(
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
            color = AIKTheme.colors.on_core_container,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

