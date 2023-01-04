package com.phil.airinkorea.ui.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.icon.AikIcons
import com.phil.airinkorea.ui.theme.*
import java.lang.reflect.Type

enum class DetailType {
    Main,
    Sub
}

@Preview(showBackground = true, backgroundColor = 0xFF001FC5)
@Composable
fun AikDetails(
    modifier: Modifier = Modifier
) {
    var expandedState by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        DetailsBar(
            expandedState = expandedState
        ) { expandedState = !expandedState }
        Spacer(modifier = Modifier.size(8.dp))
        DetailExpandableCard(
            expandedState = expandedState
        ) { expandedState = !expandedState }
    }
}

@Composable
fun DetailsBar(
    expandedState: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Details",
            color = Color.White,
            style = AikTypography.titleMedium
        )
        if (expandedState) {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = AikIcons.ArrowDropUp),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        } else {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = AikIcons.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailExpandableCard(
    expandedState: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(level1_primaryContainer)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                DetailsForm(
                    title = stringResource(id = R.string.pm_10),
                    value = "Very\nGood",
                    unit = stringResource(id = R.string.micro_per_square_meter, 5),
                    sourceColor = Color.Blue,
                    type = DetailType.Main,
                    modifier = Modifier
                        .weight(1f)

                )
                Divider(
                    color = dividerColor,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                DetailsForm(
                    title = stringResource(id = R.string.pm_2_5),
                    value = "Bad",
                    unit = stringResource(id = R.string.micro_per_square_meter, 5),
                    sourceColor = Color.Blue,
                    type = DetailType.Main,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            AnimatedVisibility(expandedState) {
                Divider(
                    color = dividerColor,
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
                    DetailsForm(
                        title = stringResource(id = R.string.no2),
                        value = "Very\nGood",
                        unit = stringResource(id = R.string.ppm, 5),
                        sourceColor = Color.Blue,
                        type = DetailType.Sub,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Divider(
                        color = dividerColor,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    DetailsForm(
                        title = stringResource(id = R.string.so2),
                        value = "UnHealthy",
                        unit = stringResource(id = R.string.ppm, 5),
                        sourceColor = Color.Blue,
                        type = DetailType.Sub,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Divider(
                        color = dividerColor,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    DetailsForm(
                        title = stringResource(id = R.string.CO),
                        value = "Very\nGood",
                        unit = stringResource(id = R.string.ppm, 5),
                        sourceColor = Color.Blue,
                        type = DetailType.Sub,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Divider(
                        color = dividerColor,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    DetailsForm(
                        title = stringResource(id = R.string.o3),
                        value = "Bad",
                        unit = stringResource(id = R.string.ppm, 5),
                        sourceColor = Color.Blue,
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
fun DetailsForm(
    title: String,
    value: String,
    unit: String,
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
                .padding(10.dp)
        ) {
            Text(
                text = title,
                style = AikTypography.titleMedium,
            )
            Spacer(modifier = Modifier.size(10.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(80.dp)
            ) {
                Text(
                    text = value,
                    style = AikTypography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(3.dp)
                    .background(color = sourceColor)
            )
            Spacer(modifier = Modifier.size(7.dp))
            Text(
                text = unit,
                style = AikTypography.bodyMedium
            )
        }
    } else if (type == DetailType.Sub) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Text(
                text = title,
                style = AikTypography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(50.dp)
            ) {
                Text(
                    text = value,
                    style = AikTypography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .width(35.dp)
                    .height(3.dp)
                    .background(color = sourceColor)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = unit,
                style = AikTypography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun DetailsBarPreview() {
    var expandedState by remember {
        mutableStateOf(false)
    }
    DetailsBar(
        expandedState = expandedState
    ) { expandedState = !expandedState }
}

@Preview(backgroundColor = 0xFF000A63)
@Composable
fun DetailsExpandableCardPreview() {
    var expandedState by remember {
        mutableStateOf(true)
    }
    DetailExpandableCard(expandedState = expandedState) {
        expandedState = !expandedState
    }
}
