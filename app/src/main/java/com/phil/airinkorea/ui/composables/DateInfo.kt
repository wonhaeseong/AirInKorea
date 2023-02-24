package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel

@Composable
fun DateInfo(
    date: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
        contentColor = AIKTheme.colors.on_core,
        modifier = modifier.fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.body2,
            color = AIKTheme.colors.on_core,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun DataInfoPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.FINE) {
        DateInfo(date = "Saturday, January 21, 2023 9:10 PM")
    }
}