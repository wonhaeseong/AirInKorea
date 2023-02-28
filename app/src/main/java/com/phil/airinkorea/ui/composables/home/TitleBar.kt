package com.phil.airinkorea.ui.commoncomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel

@Composable
fun TitleBar(
    titleText: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = titleText,
            color = AIKTheme.colors.on_core,
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun ExpendableTitleBar(
    expandedState: Boolean,
    titleText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = titleText,
            color = AIKTheme.colors.on_core,
            style = MaterialTheme.typography.subtitle1
        )
        if (expandedState) {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = AIKIcons.ArrowDropUp),
                    contentDescription = null,
                    tint = AIKTheme.colors.on_core
                )
            }
        } else {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = AIKIcons.ArrowDropDown,
                    contentDescription = null,
                    tint = AIKTheme.colors.on_core
                )
            }
        }
    }
}

@Preview
@Composable
fun TitleBarPreview(){
    AIKTheme(PollutionLevel.GOOD){
        TitleBar(titleText = "Detail")
    }
}

@Preview
@Composable
fun ExpendableTitleBarPreview() {
    var expandedState by remember {
        mutableStateOf(false)
    }
    AIKTheme(PollutionLevel.GOOD) {
        ExpendableTitleBar(
            expandedState = expandedState,
            titleText = "Detail",
            onClick = { expandedState = !expandedState }
        )
    }
}