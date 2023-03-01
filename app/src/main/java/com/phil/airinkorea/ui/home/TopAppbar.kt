package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel

private val iconSize = 24.dp

@Composable
fun AIKTopAppBar(
    modifier: Modifier = Modifier,
    location: String,
    onMenuButtonClicked: () -> Unit = {}
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentColor = AIKTheme.colors.on_core,
        elevation = 0.dp,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                onClick = onMenuButtonClicked,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = AIKIcons.Menu,
                    contentDescription = null,
                    tint = AIKTheme.colors.on_core,
                    modifier = Modifier.size(iconSize)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = AIKIcons.Location),
                    tint = AIKTheme.colors.on_core,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.subtitle1,
                    color = AIKTheme.colors.on_core,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.FINE) {
        AIKTopAppBar(location = "dddd", onMenuButtonClicked = {})
    }
}