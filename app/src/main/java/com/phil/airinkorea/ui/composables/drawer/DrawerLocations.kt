package com.phil.airinkorea.ui.composables.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel

@Composable
fun DrawerLocations(
    modifier: Modifier = Modifier,
    locations: List<String>? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        DrawerTitle(
            icon = painterResource(id = AIKIcons.Location),
            stringId = R.string.locations
        )
        if (locations.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                DrawerItem(
                    text = stringResource(id = R.string.please_add_a_location),
                    itemEnable = false
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                items(locations) { location ->
                    DrawerItem(text = location)
                }
            }
        }
    }
}

@Preview
@Composable
fun DrawerLocationsPreview() {
    AIKTheme(PollutionLevel.EXCELLENT) {
        DrawerLocations(
            locations = listOf(
                "Anui-myeon",
                "Younghyun-Dong",
                "Euljiro 1(il)-ga",
                "Jongno 1(il)-ga",
                "Mugyo-dong"
            )
        )
    }
}