package com.phil.airinkorea.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.drawer.Bookmark
import com.phil.airinkorea.ui.drawer.DrawerLocations
import com.phil.airinkorea.ui.drawer.DrawerTitle
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel
import com.phil.airinkorea.ui.theme.divider

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    bookmarkedLocation: String = "",
    locations: List<String>? = null,
    onSettingButtonClick: () -> Unit = {},
    onManageLocationClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .clickable(false){}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = AIKTheme.colors.core_container,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(horizontal = 15.dp)
                .verticalScroll(scrollState)
        ) {
            //setting
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                IconButton(onClick = onSettingButtonClick) {
                    Icon(imageVector = AIKIcons.Setting, tint = AIKTheme.colors.on_core_container,contentDescription = null)
                }
            }
            //bookmark
            Bookmark(bookmarkedLocation = bookmarkedLocation)

            Divider(
                color = divider, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            //locations
            DrawerLocations(locations = locations)

            //manage locations Button
            TextButton(
                onClick = onManageLocationClick,
                colors = ButtonDefaults.textButtonColors(AIKTheme.colors.core_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.manage_locations),
                    style = MaterialTheme.typography.button,
                    overflow = TextOverflow.Ellipsis,
                    color = AIKTheme.colors.core_container
                )
            }

            Divider(
                color = divider, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            //particulate Matter Info 미세먼지 정보
            DrawerTitle(
                icon = painterResource(id = AIKIcons.Info),
                stringId = R.string.particulate_matter_info,
                clickable = true
            )
            //App Info 앱 정보
            DrawerTitle(
                icon = painterResource(id = AIKIcons.AppIcon),
                tint = Color.White,
                iconBackgroundColor = colorResource(id = R.color.ic_aik_background),
                stringId = R.string.app_info,
                clickable = true
            )
        }
    }
}


@Preview
@Composable
fun DrawerPreview() {
    AIKTheme(PollutionLevel.EXCELLENT) {
        Drawer()
    }
}
