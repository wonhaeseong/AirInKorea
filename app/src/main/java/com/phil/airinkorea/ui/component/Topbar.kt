package com.phil.airinkorea.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.model.AirLevel
import com.phil.airinkorea.ui.icon.AikIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel
import com.phil.airinkorea.ui.theme.bookmark


@Composable
fun AikTopAppBar(
    modifier: Modifier = Modifier,
    location: String
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Icon(
                imageVector = AikIcons.Menu,
                contentDescription = null,
                tint = AIKTheme.colors.on_core,
                modifier = Modifier
                    .size(24.dp)
            )
            LocationInfoText(
                location = location
            )
            Icon(
                painter = painterResource(id = AikIcons.Star),
                contentDescription = null,
                tint = bookmark,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        AirValueText(
            airLevel = AirLevel.Level3
        )
    }
}

@Composable
fun LocationInfoText(
    modifier: Modifier = Modifier,
    location: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = AikIcons.Location),
            contentDescription = null,
            tint = AIKTheme.colors.on_core
        )
        Text(
            text = location,
            style = MaterialTheme.typography.subtitle1,
            color = AIKTheme.colors.on_core,
            modifier = modifier
        )
    }
}

@Composable
fun AirValueText(
    modifier: Modifier = Modifier,
    airLevel: AirLevel
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .offset(x = 0.dp, y = 5.dp)
    ) {
        Text(
            text = airLevel.value,
            style = MaterialTheme.typography.h4,
            color = AIKTheme.colors.on_core
        )
    }
}