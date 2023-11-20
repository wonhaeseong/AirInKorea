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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.SelectedLocation
import com.phil.airinkorea.data.model.UserLocation
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.bookmark
import com.phil.airinkorea.ui.theme.divider
import com.phil.airinkorea.ui.theme.heart
import com.phil.airinkorea.ui.theme.icon.AIKIcons
import com.phil.airinkorea.viewmodel.DrawerUiState

@Composable
fun DrawerScreen(
    modifier: Modifier = Modifier,
    drawerUiState: DrawerUiState,
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    onDrawerLocationClick: (UserLocation?) -> Unit
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
            Spacer(modifier = Modifier.size(30.dp))
            //GPS
            GPS(
                userLocation = drawerUiState.gps,
                onClick = { onDrawerLocationClick(it) },
                selectedLocation = drawerUiState.selectedLocation
            )

            Spacer(modifier = Modifier.size(3.dp))
            Divider(
                color = divider, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )
            //bookmark
            Bookmark(
                userLocation = drawerUiState.bookmark,
                onClick = { onDrawerLocationClick(it) },
                selectedLocation = drawerUiState.selectedLocation
            )
            Spacer(modifier = Modifier.size(3.dp))
            Divider(
                color = divider, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            //My locations
            MyLocations(
                userLocationList = drawerUiState.userLocationList,
                onClick = { onDrawerLocationClick(it) }
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
                    style = AIKTheme.typography.button,
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
                stringId = R.string.app_guide,
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

@Composable
fun GPS(
    modifier: Modifier = Modifier,
    userLocation: UserLocation?,
    onClick: (UserLocation?) -> Unit,
    selectedLocation: SelectedLocation
) {
    Column(modifier = modifier) {
        DrawerTitle(
            icon = painterResource(id = AIKIcons.Location),
            stringId = R.string.gps
        )
        if (userLocation == null) {
            DrawerItem(
                text = stringResource(id = R.string.unable_gps),
                onClick = { onClick(null) },
                isSelected = SelectedLocation.GPS == selectedLocation,
                hasData = false
            )
        } else {
            DrawerItem(
                text = userLocation.location.eupmyeondong, onClick = { onClick(userLocation) },
                isSelected = SelectedLocation.GPS == selectedLocation
            )
        }
    }
}

@Composable
fun Bookmark(
    modifier: Modifier = Modifier,
    userLocation: UserLocation?,
    onClick: (UserLocation) -> Unit,
    selectedLocation: SelectedLocation
) {
    Column(modifier = modifier) {
        DrawerTitle(
            icon = painterResource(id = AIKIcons.Star),
            tint = bookmark,
            stringId = R.string.bookmark
        )
        if (userLocation == null) {
            DrawerItem(
                text = stringResource(id = R.string.bookmark_is_not_set),
                itemEnable = false,
                isSelected = SelectedLocation.Bookmark == selectedLocation,
                hasData = false
            )
        } else {
            DrawerItem(
                text = userLocation.location.eupmyeondong, onClick = { onClick(userLocation) },
                isSelected = SelectedLocation.Bookmark == selectedLocation
            )
        }
    }
}

@Composable
fun MyLocations(
    modifier: Modifier = Modifier,
    userLocationList: List<UserLocation>,
    onClick: (UserLocation) -> Unit
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
        if (userLocationList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                DrawerItem(
                    text = stringResource(id = R.string.please_add_a_location),
                    itemEnable = false,
                    hasData = false
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                itemsIndexed(userLocationList) { index, userLocation ->
                    DrawerItem(
                        text = userLocation.location.eupmyeondong,
                        onClick = { onClick(userLocation) },
                        isSelected = userLocation.isSelected
                    )
                }
            }
        }
    }
}


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
            style = AIKTheme.typography.subtitle1,
            color = AIKTheme.colors.on_core_container
        )
    }
}

@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    text: String,
    itemEnable: Boolean = true,
    hasData: Boolean = true,
    onClick: () -> Unit = {},
    isSelected: Boolean = false
) {
    val contentColor = if (isSelected) AIKTheme.colors.core else AIKTheme.colors.on_core_container

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = itemEnable) { onClick() }
            .background(
                if (isSelected) AIKTheme.colors.core_20 else AIKTheme.colors.core_container
            )
            .padding(PaddingValues(start = 15.dp, top = 8.dp, bottom = 8.dp))
    ) {
        if (hasData) {
            Icon(
                painter = painterResource(id = R.drawable.ic_circle),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier
                    .size(5.dp)
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = text,
            style = AIKTheme.typography.subtitle1,
            color = contentColor,
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun DrawerPreviewSuccess() {
    AIKTheme(AirLevel.Level1) {
        DrawerScreen(
            drawerUiState = DrawerUiState(
                gps = UserLocation(
                    location =
                    Location(
                        `do` = "Gangwon-do",
                        sigungu = "Gangneung-si",
                        eupmyeondong = "Gangdong-myeon",
                        station = "옥천동"
                    ), isGPS = true,
                    isBookmark = false,
                    isSelected = true
                ),
                bookmark = UserLocation(
                    location =
                    Location(
                        `do` = "Gangwon-do",
                        sigungu = "Gangneung-si",
                        eupmyeondong = "Gangdong-myeon",
                        station = "옥천동"
                    ), isGPS = false,
                    isBookmark = true,
                    isSelected = false
                ),
                userLocationList = listOf(
                    UserLocation(
                        location =
                        Location(
                            `do` = "Gangwon-do",
                            sigungu = "Gangneung-si",
                            eupmyeondong = "Gangdong-myeon",
                            station = "옥천동"
                        ), isGPS = false,
                        isBookmark = false,
                        isSelected = false
                    ),
                    UserLocation(
                        location =
                        Location(
                            `do` = "Gangwon-do",
                            sigungu = "Gangneung-si",
                            eupmyeondong = "Gangdong-myeon",
                            station = "옥천동"
                        ), isGPS = false,
                        isBookmark = false,
                        isSelected = false
                    ),
                    UserLocation(
                        location =
                        Location(
                            `do` = "Gangwon-do",
                            sigungu = "Gangneung-si",
                            eupmyeondong = "Gangdong-myeon",
                            station = "옥천동"
                        ), isGPS = false,
                        isBookmark = false,
                        isSelected = false
                    ),
                    UserLocation(
                        location =
                        Location(
                            `do` = "Gangwon-do",
                            sigungu = "Gangneung-si",
                            eupmyeondong = "Gangdong-myeon",
                            station = "옥천동"
                        ), isGPS = false,
                        isBookmark = false,
                        isSelected = false
                    )
                )
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
            drawerUiState = DrawerUiState(),
            onManageLocationClick = {},
            onAppInfoClick = {},
            onParticulateMatterInfoClick = {},
            onDrawerLocationClick = {}
        )
    }
}