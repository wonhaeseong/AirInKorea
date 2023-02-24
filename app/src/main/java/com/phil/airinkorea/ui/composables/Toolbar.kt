package com.phil.airinkorea.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.icon.AikIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel
import kotlin.math.roundToInt

private val iconSize = 24.dp
@Composable
fun CollapsingToolbar(
    progress: Float,
    onMenuButtonClicked: () -> Unit,
    location: String,
    date: String,
    airLevel: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = AIKTheme.colors.core,
        elevation = 0.dp, //접혀 있을 때 - progress : 0f elevation:2dp 펼쳐 있을 떄- progress: 1f elevation: 0dp ((-Elevation * progress) + Elevation)
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ){
            CollapsingToolbarLayout {
                //Menu Button
                IconButton(
                    onClick = onMenuButtonClicked,
                ) {
                    Icon(
                        imageVector = AikIcons.Menu,
                        contentDescription = null,
                        tint = AIKTheme.colors.on_core,
                        modifier = Modifier.size(iconSize)
                    )
                }

                //Location
                Row(
                    modifier = Modifier.wrapContentSize()
                ) {
                    Icon(
                        painter = painterResource(id = AikIcons.Location),
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

                //DateInfo
                Text(
                    text = date,
                    style = MaterialTheme.typography.body2,
                    color = AIKTheme.colors.on_core,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.graphicsLayer {
                        alpha = progress
                    }
                )

                //AirValueText
                Text(
                    text = airLevel,
                    style = MaterialTheme.typography.h4,
                    color = AIKTheme.colors.on_core,
                    modifier = Modifier.graphicsLayer {
                        alpha = progress
                    }
                )
            }
        }
    }
}

@Composable
private fun CollapsingToolbarLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 4)
        val placeables = measurables.map {
            it.measure(constraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            val menuBtn = placeables[0]
            val location = placeables[1]
            val date = placeables[2]
            val airLevel = placeables[3]

            menuBtn.placeRelative(x = 0, y = 0)
            location.placeRelative(x = (constraints.maxWidth - location.width) / 2, y = (12.dp).toPx().roundToInt())
            date.placeRelative(x = (constraints.maxWidth - date.width) / 2, y = location.height + (20.dp).toPx().roundToInt())
            airLevel.placeRelative(
                x = (constraints.maxWidth - airLevel.width) / 2,
                y = date.height + location.height + (55.dp).toPx().roundToInt()
            )
        }
    }
}

@Preview
@Composable
fun CollapsingToolbarCollapsedPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.FINE) {
        CollapsingToolbar(
            progress = 0f,
            onMenuButtonClicked = {},
            location = "HamYang-Gun",
            date = "Saturday, January 21, 2023 9:10 PM",
            airLevel = "FINE",
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}

@Preview
@Composable
fun CollapsingToolbarHalfwayPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.FINE) {
        CollapsingToolbar(
            progress = 0.5f,
            onMenuButtonClicked = {},
            location = "HamYang-Gun",
            date = "Saturday, January 21, 2023 9:10 PM",
            airLevel = "FINE",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}

@Preview
@Composable
fun CollapsingToolbarExpandedPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.FINE) {
        CollapsingToolbar(
            progress = 1f,
            onMenuButtonClicked = {},
            location = "HamYang-Gun",
            date = "Saturday, January 21, 2023 9:10 PM",
            airLevel = "FINE",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}
