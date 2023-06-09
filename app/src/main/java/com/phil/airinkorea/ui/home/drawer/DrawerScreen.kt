package com.phil.airinkorea.ui.home.drawer

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.AIKTypography
import com.phil.airinkorea.ui.theme.Shapes
import com.phil.airinkorea.ui.theme.bookmark
import com.phil.airinkorea.ui.theme.divider
import com.phil.airinkorea.ui.theme.heart
import com.phil.airinkorea.ui.theme.icon.AIKIcons
import com.phil.airinkorea.ui.theme.subtitle3
import com.phil.airinkorea.ui.theme.transparent_white
import com.phil.airinkorea.ui.viewmodel.DrawerUiState

@Composable
fun DrawerScreen(
    modifier: Modifier = Modifier,
    drawerUiState: DrawerUiState,
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    onDrawerLocationClick: (Int) -> Unit
) {
    when (drawerUiState) {
        DrawerUiState.Loading -> Unit
        is DrawerUiState.Success -> {
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
                    GPS(
                        location = drawerUiState.gps,
                        onClick = { onDrawerLocationClick(0) },
                        page = drawerUiState.page
                    )
                    Divider(
                        color = divider, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    )
                    //bookmark
                    Bookmark(
                        location = drawerUiState.bookmark,
                        onClick = { onDrawerLocationClick(1) },
                        page = drawerUiState.page
                    )
                    Divider(
                        color = divider, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    )

                    //My locations
                    MyLocations(
                        locationList = drawerUiState.userLocationList,
                        onClick = { index -> onDrawerLocationClick(index) },
                        page = drawerUiState.page
                    )

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
                    Spacer(modifier = Modifier.size(10.dp))
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
    }
}

@Composable
fun GPS(
    modifier: Modifier = Modifier,
    location: Location?,
    onClick: (Int) -> Unit,
    page: Int
) {
    Column(modifier = modifier) {
        DrawerTitle(
            icon = painterResource(id = AIKIcons.Location),
            stringId = R.string.gps
        )
        if (location == null) {
            DrawerItem(
                text = stringResource(id = R.string.unable_gps),
                onClick = { onClick(0) },
                isSelected = page == 0
            )
        } else {
            DrawerItem(
                text = location.eupmyeondong, onClick = { onClick(0) },
                isSelected = page == 0
            )
        }
    }
}

@Composable
fun Bookmark(
    modifier: Modifier = Modifier,
    location: Location?,
    onClick: (Int) -> Unit,
    page: Int
) {
    Column(modifier = modifier) {
        DrawerTitle(
            icon = painterResource(id = AIKIcons.Star),
            tint = bookmark,
            stringId = R.string.bookmark
        )
        if (location == null) {
            DrawerItem(
                text = stringResource(id = R.string.bookmark_is_not_set),
                itemEnable = false,
                isSelected = page == 1
            )
        } else {
            DrawerItem(
                text = location.eupmyeondong, onClick = { onClick(1) },
                isSelected = page == 1
            )
        }
    }
}

@Composable
fun MyLocations(
    modifier: Modifier = Modifier,
    locationList: List<Location>,
    onClick: (Int) -> Unit,
    page: Int
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
        if (locationList.isEmpty()) {
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
                itemsIndexed(locationList) { index, location ->
                    DrawerItem(
                        text = location.eupmyeondong,
                        onClick = { onClick(index + 2) },
                        isSelected = page == index + 2
                    )
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
            .padding(start = 15.dp, end = 0.dp, top = 8.dp, bottom = 3.dp)
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
    onClick: () -> Unit = {},
    isSelected: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(PaddingValues(start = 15.dp, top = 10.dp, bottom = 12.dp))
            .clickable(enabled = itemEnable) { onClick() }
    ) {
        if (isSelected) {
            Box(modifier = Modifier.background(Color.Gray, Shapes.medium)) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.h6,
                    color = AIKTheme.colors.core_container,
                    textAlign = TextAlign.Start,
                    modifier = modifier.padding(horizontal = 10.dp)
                )
            }
//            Icon(
//                painter = painterResource(id = AIKIcons.Circle),
//                contentDescription = null,
//                tint = AIKTheme.colors.core,
//                modifier = Modifier.padding(end = 5.dp)
//            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1,
                color = AIKTheme.colors.on_core_container,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
fun DrawerPreviewSuccess() {
    AIKTheme(AirLevel.Level1) {
        DrawerScreen(
            drawerUiState = DrawerUiState.Success(
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
                userLocationList = listOf(
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
                    )
                ),
                page = 0
            ),
            onManageLocationClick = {},
            onAppInfoClick = {},
            onParticulateMatterInfoClick = {},
            onDrawerLocationClick = { }
        )
    }
}

@Preview
@Composable
fun DrawerPreviewEmptyData() {
    AIKTheme(AirLevel.Level1) {
        DrawerScreen(
            drawerUiState = DrawerUiState.Success(),
            onManageLocationClick = {},
            onAppInfoClick = {},
            onParticulateMatterInfoClick = {},
            onDrawerLocationClick = {}
        )
    }
}