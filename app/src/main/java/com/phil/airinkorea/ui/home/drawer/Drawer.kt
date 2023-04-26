package com.phil.airinkorea.ui.home.drawer

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.domain.model.AirLevel
import com.phil.airinkorea.domain.model.Location
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.bookmark
import com.phil.airinkorea.ui.theme.divider
import com.phil.airinkorea.ui.theme.heart
import com.phil.airinkorea.ui.theme.icon.AIKIcons
import com.phil.airinkorea.ui.viewmodel.DrawerUiState

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerUiState: DrawerUiState,
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .clickable(false) {}
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
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = AIKIcons.Setting,
                        tint = AIKTheme.colors.on_core_container,
                        contentDescription = null
                    )
                }
            }
            //GPS
            GPS(drawerUiState = drawerUiState)
            Divider(
                color = divider, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )
            //bookmark
            Bookmark(drawerUiState = drawerUiState)
            Divider(
                color = divider, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            //My locations
            MyLocations(drawerUiState = drawerUiState)

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
                clickable = true,
                onClick = onParticulateMatterInfoClick
            )

            //App Info 앱 정보
            DrawerTitle(
                icon = painterResource(id = AIKIcons.AppIcon),
                tint = Color.White,
                iconBackgroundColor = colorResource(id = R.color.ic_aik_background),
                stringId = R.string.app_info,
                clickable = true,
                onClick = onAppInfoClick
            )
        }
    }
}

@Composable
fun GPS(
    modifier: Modifier = Modifier,
    drawerUiState: DrawerUiState
) {
    Column(modifier = modifier) {
        DrawerTitle(
            icon = painterResource(id = AIKIcons.Location),
            stringId = R.string.gps
        )
        when (drawerUiState) {
            DrawerUiState.Loading, DrawerUiState.Failure -> {
                DrawerItem(text = "")
            }
            is DrawerUiState.Success ->
                if (drawerUiState.gps == null) {
                    DrawerItem(text = stringResource(id = R.string.unable_gps), itemEnable = false)
                } else {
                    DrawerItem(text = drawerUiState.gps.eupmyeondong)
                }
        }
    }
}

@Composable
fun Bookmark(
    modifier: Modifier = Modifier,
    drawerUiState: DrawerUiState
) {
    Column(modifier = modifier) {
        DrawerTitle(
            icon = painterResource(id = AIKIcons.Star),
            tint = bookmark,
            stringId = R.string.bookmark
        )
        when (drawerUiState) {
            DrawerUiState.Loading, DrawerUiState.Failure -> {
                DrawerItem(text = "")
            }
            is DrawerUiState.Success ->
                if (drawerUiState.bookmark == null) {
                    DrawerItem(
                        text = stringResource(id = R.string.bookmark_is_not_set),
                        itemEnable = false
                    )
                } else {
                    DrawerItem(text = drawerUiState.bookmark.eupmyeondong)
                }
        }
    }
}

@Composable
fun MyLocations(
    modifier: Modifier = Modifier,
    drawerUiState: DrawerUiState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        DrawerTitle(
            icon = painterResource(id = AIKIcons.Heart),
            stringId = R.string.my_locations,
            tint = heart
        )

        when (drawerUiState) {
            DrawerUiState.Loading, DrawerUiState.Failure -> {
                Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        DrawerItem(
                            text = "",
                            itemEnable = false
                        )
                    }
            }
            is DrawerUiState.Success -> {
                if (drawerUiState.userLocationList.isEmpty()) {
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
                        items(drawerUiState.userLocationList) { location ->
                            DrawerItem(text = location.eupmyeondong)
                        }
                    }
                }
            }
        }
    }
}

/**
 * @param icon painterResource에 전달할 resource Id
 * @param tint icon 색상
 * @param stringId 타이틀로 들어갈 String resource Id
 * @param clickable 클릭 활성화 비활성화
 */
@Composable
fun DrawerTitle(
    modifier: Modifier = Modifier,
    icon: Painter,
    tint: Color = Color.Black,
    iconBackgroundColor: Color = Color.Transparent,
    contentDescription: String? = null,
    @StringRes stringId: Int,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 0.dp, top = 15.dp, bottom = 15.dp)
            .clickable(enabled = clickable) {
                onClick()
            }
    ) {
        Icon(
            painter = icon,
            tint = tint,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(24.dp)
                .background(iconBackgroundColor)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = stringResource(id = stringId),
            style = MaterialTheme.typography.subtitle1,
            color = AIKTheme.colors.on_core_container
        )
    }
}

@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    text: String,
    itemEnable: Boolean = true,
    onClick: () -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        enabled = itemEnable,
        contentPadding = PaddingValues(start = 35.dp, top = 10.dp, bottom = 10.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = AIKTheme.colors.on_core_container,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Preview(name = "Success")
@Composable
fun DrawerPreviewSuccess() {
    val drawerUiState =
        DrawerUiState.Success(
            gps = Location(
                `do` = "Gangwon-do",
                sigungu = "Gangneung-si",
                eupmyeondong = "Gangdong-myeon",
                station = "옥천동"
            ),
            bookmark = Location(
                `do` = "Gangwon-do",
                sigungu = "Gangneung-si",
                eupmyeondong = "Gangdong-myeon",
                station = "옥천동"
            ),
            userLocationList =
            listOf(
                Location(
                    `do` = "Gangwon-do",
                    sigungu = "Gangneung-si",
                    eupmyeondong = "Gangdong-myeon",
                    station = "옥천동"
                ), Location(
                    `do` = "Gangwon-do",
                    sigungu = "Gangneung-si",
                    eupmyeondong = "Gangdong-myeon",
                    station = "옥천동"
                ), Location(
                    `do` = "Gangwon-do",
                    sigungu = "Gangneung-si",
                    eupmyeondong = "Gangdong-myeon",
                    station = "옥천동"
                ), Location(
                    `do` = "Gangwon-do",
                    sigungu = "Gangneung-si",
                    eupmyeondong = "Gangdong-myeon",
                    station = "옥천동"
                ), Location(
                    `do` = "Gangwon-do",
                    sigungu = "Gangneung-si",
                    eupmyeondong = "Gangdong-myeon",
                    station = "옥천동"
                ), Location(
                    `do` = "Gangwon-do",
                    sigungu = "Gangneung-si",
                    eupmyeondong = "Gangdong-myeon",
                    station = "옥천동"
                )
            )
        )
    AIKTheme(AirLevel.Level1) {
        Drawer(
            drawerUiState = drawerUiState,
            onManageLocationClick = {},
            onAppInfoClick = {},
            onParticulateMatterInfoClick = {}
        )
    }
}

@Preview(name = "Success_empty_data")
@Composable
fun DrawerPreviewSuccessButEmpty() {
    val drawerUiState =
        DrawerUiState.Success(
            gps = null,
            bookmark = null,
            userLocationList = emptyList()
        )
    AIKTheme(AirLevel.Level1) {
        Drawer(
            drawerUiState = drawerUiState,
            onManageLocationClick = {},
            onAppInfoClick = {},
            onParticulateMatterInfoClick = {}
        )
    }
}

@Preview(name = "Failure")
@Composable
fun DrawerPreviewFailure() {
    AIKTheme(AirLevel.Level1) {
        Drawer(
            drawerUiState = DrawerUiState.Failure,
            onManageLocationClick = {},
            onAppInfoClick = {},
            onParticulateMatterInfoClick = {}
        )
    }
}


@Preview(name = "Loading")
@Composable
fun DrawerPreviewLoading() {
    AIKTheme(AirLevel.Level1) {
        Drawer(
            drawerUiState = DrawerUiState.Loading,
            onManageLocationClick = {},
            onAppInfoClick = {},
            onParticulateMatterInfoClick = {}
        )
    }
}