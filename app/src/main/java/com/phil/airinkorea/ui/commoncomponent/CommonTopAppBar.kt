package com.phil.airinkorea.ui.commoncomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.modifier.bottomBorder
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel
import com.phil.airinkorea.ui.theme.common_background
import com.phil.airinkorea.ui.theme.divider

@Composable
fun CommonTopAppBar(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(common_background)
            .bottomBorder(1.dp, divider)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 5.dp, end = 10.dp)
                .clip(shape = CircleShape)
                .clickable { onBackButtonClick() }
        ) {
            Icon(
                painter = painterResource(id = AIKIcons.BackArrow),
                contentDescription = null,
                tint = AIKTheme.colors.on_core_container,
                modifier = Modifier.padding(10.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            color = AIKTheme.colors.on_core_container
        )
    }
}

@Preview
@Composable
fun CommonTopAppBarPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.EXCELLENT) {
        CommonTopAppBar(onBackButtonClick = {}, title = "Manage Locations")
    }
}
