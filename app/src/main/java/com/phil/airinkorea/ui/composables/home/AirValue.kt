package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel

@Composable
fun AirValue(
    modifier: Modifier = Modifier,
    airLevel: String
) {
    Surface(
        color = Color.Transparent,
        contentColor = AIKTheme.colors.on_core,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            text = airLevel,
            style = MaterialTheme.typography.h4,
            color = AIKTheme.colors.on_core,
        )
    }
}

@Preview
@Composable
fun AirValuePreview() {
    AIKTheme(pollutionLevel = PollutionLevel.FINE) {
        AirValue(airLevel = "Fine")
    }
}