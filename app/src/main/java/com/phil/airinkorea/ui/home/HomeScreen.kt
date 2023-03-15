package com.phil.airinkorea.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.airbnb.lottie.compose.*
import com.phil.airinkorea.R
import com.phil.airinkorea.model.AirLevel
import com.phil.airinkorea.ui.home.drawer.DrawerUiState
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.screen.Drawer
import com.phil.airinkorea.ui.theme.*
import com.phil.airinkorea.ui.viewmodel.DailyForecastData
import com.phil.airinkorea.ui.viewmodel.DetailData
import com.phil.airinkorea.ui.viewmodel.HomeUiState
import com.phil.airinkorea.ui.viewmodel.HourlyForecastData
import kotlinx.coroutines.launch

private val IconSize = 24.dp
private val GPSErrorIconSize = 42.5.dp
private val GapBetweenTitleAndContent = 8.dp
private val ContentElevation = 2.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    homeUiState: HomeUiState,
    drawerUiState: DrawerUiState
) {
    val pullRefreshState = rememberPullRefreshState(refreshing, onRefresh)
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    AIKTheme(airLevel = AirLevel.Level1) {
        Box(
            modifier = modifier
                .background(AIKTheme.colors.core_background)
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = Color.Transparent,
                topBar = {
                    HomeTopAppBar(
                        location = homeUiState.location,
                        onMenuButtonClicked = { scope.launch { scaffoldState.drawerState.open() } }
                    )
                },
                drawerContent = {
                    Drawer(
                        bookmarkedLocation = drawerUiState.bookmarkedLocation,
                        locations = drawerUiState.userLocationList,
                        onManageLocationClick = onManageLocationClick,
                        onParticulateMatterInfoClick = onParticulateMatterInfoClick,
                        onAppInfoClick = onAppInfoClick
                    )
                },
                drawerGesturesEnabled = true,
                drawerScrimColor = scrimColor,
                modifier = Modifier
                    .statusBarsPadding()
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                        .verticalScroll(scrollState)
                ) {
                    DateInfo(date = homeUiState.dataTime)
                    Spacer(modifier = Modifier.size(35.dp))
                    AirValue(airLevel = homeUiState.airLevel)
                    Spacer(modifier = Modifier.size(10.dp))
                    //당겨서 새로고침
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth()
                            .height(
                                if (refreshing) {
                                    140.dp
                                } else {
                                    with(LocalDensity.current) {
                                        lerp(
                                            0.dp,
                                            140.dp,
                                            pullRefreshState.progress.coerceIn(0f..1f)
                                        )
                                    }
                                }
                            )
                    ) {
                        CloudIndicator(isPlaying = refreshing)
                    }
                    Detail(
                        detailData = homeUiState.detailData,
                        modifier = Modifier.padding(10.dp)
                    )
                    HourlyForecast(
                        hourlyForecastDataList = homeUiState.hourlyForecastData,
                        contentPaddingValues = PaddingValues(horizontal = 10.dp)
                    )
                    DailyForecast(
                        dailyForecastDataList = homeUiState.dailyForecastData,
                        modifier = Modifier.padding(10.dp)
                    )
//                    Map(modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    location: String,
    onMenuButtonClicked: () -> Unit = {}
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentColor = AIKTheme.colors.on_core,
        elevation = 0.dp,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                onClick = onMenuButtonClicked,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = AIKIcons.Menu,
                    contentDescription = null,
                    tint = AIKTheme.colors.on_core,
                    modifier = Modifier.size(IconSize)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = AIKIcons.Location),
                    tint = AIKTheme.colors.on_core,
                    contentDescription = null,
                    modifier = Modifier.size(IconSize)
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.subtitle1,
                    color = AIKTheme.colors.on_core,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DateInfo(
    date: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
        contentColor = AIKTheme.colors.on_core,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.body2,
            color = AIKTheme.colors.on_core,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun AirValue(
    modifier: Modifier = Modifier,
    airLevel: String
) {
    Surface(
        color = Color.Transparent,
        contentColor = AIKTheme.colors.on_core,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            text = airLevel,
            style = MaterialTheme.typography.h4,
            color = AIKTheme.colors.on_core,
        )
    }
}

@Composable
fun CloudIndicator(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cloud))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        clipSpec = LottieClipSpec.Frame(0, 42),
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true,
        restartOnPlay = false
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun GPSBanner(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = AIKTheme.colors.core_container,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Icon(
                    painter = painterResource(id = AIKIcons.GPSError),
                    tint = error,
                    contentDescription = null,
                    modifier = Modifier.size(GPSErrorIconSize)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = stringResource(id = R.string.gps_error),
                    color = AIKTheme.colors.on_core_container,
                    style = MaterialTheme.typography.body1,
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
                    Text(
                        text = stringResource(id = R.string.dismiss),
                        color = AIKTheme.colors.core,
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.wrapContentSize()) {
                    Text(
                        text = stringResource(id = R.string.turn_on_gps),
                        color = AIKTheme.colors.core,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

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
        Spacer(modifier = Modifier.size(GapBetweenTitleAndContent))
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
        elevation = ContentElevation,
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

@Composable
fun HourlyForecast(
    modifier: Modifier = Modifier,
    hourlyForecastDataList: List<HourlyForecastData>,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TitleBar(titleText = "Hourly Forecast", modifier = Modifier.padding(contentPaddingValues))
        Spacer(modifier = Modifier.size(GapBetweenTitleAndContent))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = contentPaddingValues
        ) {
            items(hourlyForecastDataList) { data ->
                HourlyForecastComponent(data)
            }
        }
    }
}

@Composable
fun HourlyForecastComponent(
    hourlyForecastData: HourlyForecastData,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = Shapes.medium,
        color = AIKTheme.colors.core_container,
        elevation = ContentElevation,
        modifier = modifier
            .size(85.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = hourlyForecastData.time,
                style = MaterialTheme.typography.subtitle3,
                color = AIKTheme.colors.on_core_container
            )
            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )
            Text(
                text = hourlyForecastData.airLevel,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.SemiBold,
                color = AIKTheme.colors.on_core_container
            )
            Spacer(
                modifier = Modifier
                    .size(11.dp)
            )
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .width(40.dp)
                    .height(4.dp)
                    .background(hourlyForecastData.airLevelColor)

            )
        }
    }
}

@Composable
fun DailyForecast(
    modifier: Modifier = Modifier,
    dailyForecastDataList: List<DailyForecastData>
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
            titleText = "Daily Forecast",
            onClick = { expandedState = !expandedState })
        Spacer(modifier = Modifier.size(GapBetweenTitleAndContent))
        DailyForecastList(
            expandedState = expandedState,
            dailyForecastDataList = dailyForecastDataList,
            onClick = { expandedState = !expandedState }
        )
    }
}


@Composable
fun DailyForecastList(
    modifier: Modifier = Modifier,
    expandedState: Boolean,
    dailyForecastDataList: List<DailyForecastData>,
    onClick: () -> Unit
) {
    val backgroundColorList: List<Color> = listOf(
        AIKTheme.colors.core_container,
        Color.White,
        AIKTheme.colors.core_container,
        Color.White,
        AIKTheme.colors.core_container,
        Color.White,
        AIKTheme.colors.core_container
    )
    Surface(
        shape = Shapes.medium,
        elevation = ContentElevation,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            for (i in 0..2) {
                DailyForecastComponent(
                    backgroundColor = backgroundColorList[i],
                    daysOfTheWeek = dailyForecastDataList[i].daysOfTheWeek,
                    date = dailyForecastDataList[i].date,
                    airLevel = dailyForecastDataList[i].airLevel,
                    airLevelColor = dailyForecastDataList[i].airLevelColor
                )
            }
            AnimatedVisibility(expandedState) {
                Column {
                    for (i in 3..6) {
                        DailyForecastComponent(
                            backgroundColor = backgroundColorList[i],
                            daysOfTheWeek = dailyForecastDataList[i].daysOfTheWeek,
                            date = dailyForecastDataList[i].date,
                            airLevel = dailyForecastDataList[i].airLevel,
                            airLevelColor = dailyForecastDataList[i].airLevelColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DailyForecastComponent(
    backgroundColor: Color,
    daysOfTheWeek: String,
    date: String,
    airLevel: String,
    airLevelColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(backgroundColor)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = daysOfTheWeek,
                color = AIKTheme.colors.on_core_container,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = date,
                color = AIKTheme.colors.on_core_container_subtext,
                style = MaterialTheme.typography.body2
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
        ) {
            Text(
                text = airLevel,
                color = AIKTheme.colors.on_core_container,
                style = MaterialTheme.typography.body1
            )
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .width(60.dp)
                    .height(4.dp)
                    .background(color = airLevelColor)
            )
        }
    }
}

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

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(
//        refreshing =,
//        onRefresh = { /*TODO*/ },
//        onManageLocationClick = { /*TODO*/ },
//        onParticulateMatterInfoClick = { /*TODO*/ },
//        onAppInfoClick = { /*TODO*/ },
//        homeUiState =,
//        drawerUiState =
//    )
//}

//DetailData(
//pm25Level = "Dangerous",
//pm25Value = 10,
//pm25Color = level1_core,
//pm10Level = "Dangerous",
//pm10Value = 10,
//pm10Color = level2_core,
//no2Level = "Dangerous",
//no2Value = 10,
//no2Color = level3_core,
//so2Level = "Dangerous",
//so2Value = 10,
//so2Color = level4_core,
//coLevel = "Dangerous",
//coValue = 10,
//coColor = level5_core,
//o3Level = "Dangerous",
//o3Value = 10,
//o3Color = level6_core
//)

//listOf(
//HourlyForecastData(
//time = "10AM",
//airLevel = "Excellent",
//airLevelColor = level1_core
//),
//HourlyForecastData(
//time = "11AM",
//airLevel = "Good",
//airLevelColor = level2_core
//),
//HourlyForecastData(
//time = "12PM",
//airLevel = "Fine",
//airLevelColor = level3_core
//),
//HourlyForecastData(
//time = "1PM",
//airLevel = "Moderate",
//airLevelColor = level4_core
//),
//HourlyForecastData(
//time = "2PM",
//airLevel = "Poor",
//airLevelColor = level5_core
//),
//HourlyForecastData(
//time = "3PM",
//airLevel = "Bad",
//airLevelColor = level6_core
//),
//HourlyForecastData(
//time = "4PM",
//airLevel = "Unhealthy",
//airLevelColor = level7_core
//),
//HourlyForecastData(
//time = "5PM",
//airLevel = "Dangerous",
//airLevelColor = level8_core
//)
//)

//listOf(
//DailyForecastData("Sunday", "12/16/2022", "Excellent", level1_core),
//DailyForecastData("Monday", "12/17/2022", "Good", level2_core),
//DailyForecastData("Tuesday", "12/18/2022", "Fine", level3_core),
//DailyForecastData("Wednesday", "12/19/2022", "Moderate", level4_core),
//DailyForecastData("Thursday", "12/20/2022", "Poor", level5_core),
//DailyForecastData("Friday", "12/21/2022", "Bad", level6_core),
//DailyForecastData("Saturday", "12/22/2022", "Unhealthy", level7_core)
//)