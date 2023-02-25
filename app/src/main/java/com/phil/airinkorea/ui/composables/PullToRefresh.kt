package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.phil.airinkorea.R

@Composable
fun CloudIndicator(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cloud))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        clipSpec = LottieClipSpec.Frame(0,42),
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true,
        restartOnPlay = false
    )
    LottieAnimation(composition = composition, progress = { progress }, modifier = modifier.fillMaxSize())
}