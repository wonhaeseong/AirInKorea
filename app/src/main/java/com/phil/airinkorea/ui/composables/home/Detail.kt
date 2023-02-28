package com.phil.airinkorea.ui.composables.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.model.DetailData
import com.phil.airinkorea.ui.commoncomponents.ExpendableTitleBar
import com.phil.airinkorea.ui.theme.*

enum class DetailType {
    Main,
    Sub
}

@Composable
fun Detail(
    detailData: DetailData,
    modifier: Modifier = Modifier
) {
    var expandedState by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        ExpendableTitleBar(
            expandedState = expandedState,
            titleText = "Details",
            onClick = { expandedState = !expandedState })
        Spacer(modifier = Modifier.size(8.dp))
        ExpendableDetailsGrid(
            detailData = detailData,
            expandedState = expandedState,
            onClick = { expandedState = !expandedState })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpendableDetailsGrid(
    detailData: DetailData,
    expandedState: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .background(AIKTheme.colors.core_container)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                DetailLayout(
                    title = stringResource(id = R.string.pm_10),
                    level = detailData.pm10Level,
                    value = stringResource(
                        id = R.string.micro_per_square_meter,
                        detailData.pm10Value
                    ),
                    sourceColor = detailData.pm10Color,
                    type = DetailType.Main,
                    modifier = Modifier
                        .weight(1f)

                )
                Divider(
                    color = divider,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                DetailLayout(
                    title = stringResource(id = R.string.pm_2_5),
                    level = detailData.pm25Level,
                    value = stringResource(
                        id = R.string.micro_per_square_meter,
                        detailData.pm25Value
                    ),
                    sourceColor = detailData.pm25Color,
                    type = DetailType.Main,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            AnimatedVisibility(expandedState) {
                Divider(
                    color = divider,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    DetailLayout(
                        title = stringResource(id = R.string.no2),
                        level = detailData.no2Level,
                        value = stringResource(id = R.string.ppm, detailData.no2Value),
                        sourceColor = detailData.no2Color,
                        type = DetailType.Sub,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Divider(
                        color = divider,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    DetailLayout(
                        title = stringResource(id = R.string.so2),
                        level = detailData.so2Level,
                        value = stringResource(id = R.string.ppm, detailData.so2Value),
                        sourceColor = detailData.so2Color,
                        type = DetailType.Sub,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Divider(
                        color = divider,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    DetailLayout(
                        title = stringResource(id = R.string.CO),
                        level = detailData.coLevel,
                        value = stringResource(id = R.string.ppm, detailData.coValue),
                        sourceColor = detailData.coColor,
                        type = DetailType.Sub,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Divider(
                        color = divider,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    DetailLayout(
                        title = stringResource(id = R.string.o3),
                        level = detailData.o3Level,
                        value = stringResource(id = R.string.ppm, detailData.o3Value),
                        sourceColor = detailData.o3Color,
                        type = DetailType.Sub,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailLayout(
    title: String,
    level: String,
    value: String,
    sourceColor: Color,
    type: DetailType,
    modifier: Modifier = Modifier
) {
    if (type == DetailType.Main) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .wrapContentHeight()
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                color = AIKTheme.colors.on_core_container
            )
            Spacer(modifier = Modifier.size(5.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(80.dp)
            ) {
                Text(
                    text = level,
                    style = MaterialTheme.typography.h5,
                    color = AIKTheme.colors.on_core_container,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .width(70.dp)
                    .height(6.dp)
                    .background(color = sourceColor)
            )
            Spacer(modifier = Modifier.size(7.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.subtitle1,
                color = AIKTheme.colors.on_core_container
            )
        }
    } else if (type == DetailType.Sub) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .wrapContentHeight()
                .padding(horizontal = 5.dp, vertical = 13.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                color = AIKTheme.colors.on_core_container
            )
            Spacer(modifier = Modifier.size(4.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(50.dp)
            ) {
                Text(
                    text = level,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = AIKTheme.colors.on_core_container,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .width(45.dp)
                    .height(4.dp)
                    .background(color = sourceColor)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.subtitle2,
                color = AIKTheme.colors.on_core_container
            )
        }
    }
}

@Preview
@Composable
fun DetailPreview() {
    AIKTheme(PollutionLevel.GOOD) {
        Detail(
            detailData = DetailData(
                pm25Level = "Dangerous",
                pm25Value = 10,
                pm25Color = level1_core,
                pm10Level = "Dangerous",
                pm10Value = 10,
                pm10Color = level2_core,
                no2Level = "Dangerous",
                no2Value = 10,
                no2Color = level3_core,
                so2Level = "Dangerous",
                so2Value = 10,
                so2Color = level4_core,
                coLevel = "Dangerous",
                coValue = 10,
                coColor = level5_core,
                o3Level = "Dangerous",
                o3Value = 10,
                o3Color = level6_core
            )
        )
    }
}
