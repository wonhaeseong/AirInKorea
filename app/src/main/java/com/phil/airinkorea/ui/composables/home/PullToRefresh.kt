package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.airbnb.lottie.compose.*
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.theme.AIKTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CloudIndicator(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cloud))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        clipSpec = LottieClipSpec.Frame(0, 42),
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true,
        restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize()
    )
}