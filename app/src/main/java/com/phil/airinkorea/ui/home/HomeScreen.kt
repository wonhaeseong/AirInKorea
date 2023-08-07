package com.phil.airinkorea.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.compose.*
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.phil.airinkorea.R
import com.phil.airinkorea.data.model.AirLevel
import com.phil.airinkorea.data.model.DailyForecast
import com.phil.airinkorea.data.model.DetailAirData
import com.phil.airinkorea.data.model.KoreaForecastModelGif
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.ui.home.drawer.DrawerScreen
import com.phil.airinkorea.ui.theme.*
import com.phil.airinkorea.ui.theme.icon.AIKIcons
import com.phil.airinkorea.ui.viewmodel.DrawerUiState
import com.phil.airinkorea.ui.viewmodel.HomeUiState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val IconSize = 24.dp
private val GPSErrorIconSize = 42.5.dp
private val GapBetweenTitleAndContent = 8.dp
private val ContentElevation = 2.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onManageLocationClick: () -> Unit,
    onParticulateMatterInfoClick: () -> Unit,
    onAppInfoClick: () -> Unit,
    onDrawerLocationClick: (Int) -> Unit,
    homeUiState: HomeUiState,
    drawerUiState: DrawerUiState
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    when (homeUiState) {
        HomeUiState.Initializing -> {
            HomeInitialLoadingScreen()
        }

        is HomeUiState.Success -> {
            val pullRefreshState = rememberPullRefreshState(homeUiState.isRefreshing, onRefresh)
            AIKTheme(airLevel = homeUiState.airLevel) {
                Box(
                    modifier = modifier
                        .background(AIKTheme.colors.core_background)
                ) {
                    if (homeUiState.isPageLoading) {
                        PageLoadingScreen(
                            modifier = Modifier.zIndex(2.0f),
                        )
                    }
                    Scaffold(
                        scaffoldState = scaffoldState,
                        backgroundColor = Color.Transparent,
                        topBar = {
                            HomeTopAppBar(
                                isGPS = homeUiState.page == 0,
                                location = homeUiState.location,
                                onMenuButtonClicked = { scope.launch { scaffoldState.drawerState.open() } }
                            )
                        },
                        drawerContent = {
                            DrawerScreen(
                                drawerUiState = drawerUiState,
                                onManageLocationClick = onManageLocationClick,
                                onParticulateMatterInfoClick = onParticulateMatterInfoClick,
                                onAppInfoClick = onAppInfoClick,
                                onDrawerLocationClick = {
                                    scope.launch { scaffoldState.drawerState.close() }
                                    onDrawerLocationClick(it)
                                }
                            )
                        },
                        drawerGesturesEnabled = true,
                        drawerScrimColor = scrim_color,
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
                                        if (homeUiState.isRefreshing) {
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
                                CloudIndicator(isPlaying = homeUiState.isRefreshing)
                            }
                            if (homeUiState.airLevel == AirLevel.LevelError) {
                                val isUnderMaintenance: Boolean = homeUiState.dataTime != null
                                ErrorBanner(
                                    isUnderMaintenance = isUnderMaintenance,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Detail(
                                detailData = homeUiState.detailAirData,
                                modifier = Modifier.padding(10.dp)
                            )
                            Information(
                                information = homeUiState.information,
                                modifier = Modifier.padding(10.dp)
                            )
                            DailyForecast(
                                dailyForecastDataList = homeUiState.dailyForecast,
                                modifier = Modifier.padding(10.dp)
                            )
                            KoreaForecastMap(
                                gifUrl = homeUiState.forecastModelUrl,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeInitialLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = level1_core_container)
    ) {
        LoadingIndicator(modifier = Modifier.size(250.dp))
    }
}

@Composable
fun PageLoadingScreen(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cloud))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = true,
        clipSpec = LottieClipSpec.Frame(0, 42),
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true,
        restartOnPlay = false
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = SimpleColorFilter(common_background.toArgb()),
            keyPath = arrayOf("**")
        )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = transparent_white)
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            dynamicProperties = dynamicProperties,
            modifier = modifier.size(200.dp)
        )
    }
}

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = true
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loadingdots))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever,
        reverseOnRepeat = true,
        restartOnPlay = false,
        speed = 1.0f
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    isGPS: Boolean,
    location: Location?,
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
                if (isGPS) {
                    Icon(
                        painter = painterResource(id = AIKIcons.Location),
                        tint = AIKTheme.colors.on_core,
                        contentDescription = null,
                        modifier = Modifier.size(IconSize)
                    )
                }
                Text(
                    text = location?.eupmyeondong ?: "???",
                    style = AIKTheme.typography.subtitle2,
                    color = AIKTheme.colors.on_core,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DateInfo(
    date: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
        contentColor = AIKTheme.colors.on_core,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(5.dp)
    ) {
        Text(
            text = date ?: "",
            style = AIKTheme.typography.subtitle2,
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
    airLevel: AirLevel
) {
    Surface(
        color = Color.Transparent,
        contentColor = AIKTheme.colors.on_core,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            text = airLevel.value,
            style = AIKTheme.typography.h4,
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
fun ErrorBanner(
    isUnderMaintenance: Boolean,
    modifier: Modifier = Modifier
) {
    val text =
        if (isUnderMaintenance) {
            stringResource(id = R.string.under_maintenance)
        } else {
            stringResource(id = R.string.data_error)
        }
    Surface(
        shape = AIKTheme.shapes.medium,
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
                    painter = painterResource(id = AIKIcons.Error),
                    tint = error,
                    contentDescription = null,
                    modifier = Modifier.size(GPSErrorIconSize)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = text,
                    color = AIKTheme.colors.on_core_container,
                    style = AIKTheme.typography.body1,
                )
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
    detailData: DetailAirData,
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
            titleText = stringResource(id = R.string.details),
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
    detailData: DetailAirData,
    expandedState: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        elevation = ContentElevation,
        shape = AIKTheme.shapes.medium,
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
                        detailData.pm10Value ?: "?"
                    ),
                    sourceColor = detailData.pm10Level.mapToColor(),
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
                        detailData.pm25Value ?: "?"
                    ),
                    sourceColor = detailData.pm25Level.mapToColor(),
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
                        value = stringResource(
                            id = R.string.ppm,
                            detailData.no2Value ?: "?"
                        ),
                        sourceColor = detailData.no2Level.mapToColor(),
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
                        value = stringResource(
                            id = R.string.ppm,
                            detailData.so2Value ?: "?"
                        ),
                        sourceColor = detailData.so2Level.mapToColor(),
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
                        value = stringResource(
                            id = R.string.ppm, detailData.coValue ?: "?"
                        ),
                        sourceColor = detailData.coLevel.mapToColor(),
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
                        value = stringResource(
                            id = R.string.ppm,
                            detailData.o3Value ?: "?"
                        ),
                        sourceColor = detailData.o3Level.mapToColor(),
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
    level: AirLevel,
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
                style = AIKTheme.typography.subtitle1,
                color = AIKTheme.colors.on_core_container
            )
            Spacer(modifier = Modifier.size(5.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(80.dp)
            ) {
                Text(
                    text = level.value,
                    style = AIKTheme.typography.h5,
                    color = AIKTheme.colors.on_core_container,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Box(
                modifier = Modifier
                    .clip(AIKTheme.shapes.medium)
                    .width(70.dp)
                    .height(6.dp)
                    .background(color = sourceColor)
            )
            Spacer(modifier = Modifier.size(7.dp))
            Text(
                text = value,
                style = AIKTheme.typography.subtitle1,
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
                style = AIKTheme.typography.subtitle1,
                color = AIKTheme.colors.on_core_container
            )
            Spacer(modifier = Modifier.size(4.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(50.dp)
            ) {
                Text(
                    text = level.value,
                    style = AIKTheme.typography.subtitle3,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = AIKTheme.colors.on_core_container,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Box(
                modifier = Modifier
                    .clip(AIKTheme.shapes.medium)
                    .width(45.dp)
                    .height(4.dp)
                    .background(color = sourceColor)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = value,
                style = AIKTheme.typography.body2,
                fontWeight = FontWeight.SemiBold,
                color = AIKTheme.colors.on_core_container
            )
        }
    }
}

@Composable
fun DailyForecast(
    modifier: Modifier = Modifier,
    dailyForecastDataList: List<DailyForecast>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TitleBar(titleText = stringResource(id = R.string.dailyForecast))
        Spacer(modifier = Modifier.size(GapBetweenTitleAndContent))
        DailyForecastList(dailyForecastDataList = dailyForecastDataList)
    }
}


@Composable
fun DailyForecastList(
    modifier: Modifier = Modifier,
    dailyForecastDataList: List<DailyForecast>,
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
    ) {
        Column {
            for (i in dailyForecastDataList.indices) {
                val date =
                    dailyForecastDataList[i].date?.let {
                        val originalFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        LocalDate.parse(it, originalFormat)
                    }
                DailyForecastComponent(
                    backgroundColor = backgroundColorList[i],
                    daysOfTheWeek = date?.dayOfWeek?.name ?: "?",
                    date = date?.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) ?: "?",
                    airLevel = dailyForecastDataList[i].airLevel.value,
                    airLevelColor = dailyForecastDataList[i].airLevel.mapToColor()
                )
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
                style = AIKTheme.typography.body2
            )
            Text(
                text = date,
                color = AIKTheme.colors.on_core_container_subtext,
                style = AIKTheme.typography.body2
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
        ) {
            Text(
                text = airLevel,
                color = AIKTheme.colors.on_core_container,
                style = AIKTheme.typography.subtitle2
            )
            Box(
                modifier = Modifier
                    .clip(AIKTheme.shapes.medium)
                    .width(50.dp)
                    .height(5.dp)
                    .background(color = airLevelColor)
            )
        }
    }
}

@Composable
fun Information(
    information: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TitleBar(titleText = stringResource(id = R.string.information))
        Spacer(modifier = Modifier.size(GapBetweenTitleAndContent))
        Surface(
            shape = Shapes.medium,
            elevation = ContentElevation,
            color = AIKTheme.colors.core_container,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = information ?: stringResource(id = R.string.no_information_data),
                style = AIKTheme.typography.subtitle2,
                color = AIKTheme.colors.on_core_container,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
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
            style = AIKTheme.typography.subtitle1
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
            style = AIKTheme.typography.subtitle1
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun KoreaForecastMap(
    modifier: Modifier = Modifier,
    gifUrl: KoreaForecastModelGif
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TitleBar(titleText = stringResource(id = R.string.koreaForecastMap))
        Spacer(modifier = Modifier.size(GapBetweenTitleAndContent))
        Surface(
            shape = Shapes.medium,
            modifier = Modifier.wrapContentSize(),
            color = AIKTheme.colors.core_container
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (gifUrl.pm10GifUrl != null) {
                    GlideImage(
                        model = gifUrl.pm10GifUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_error_outline),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            text = stringResource(id = R.string.korea_forecast_model_error),
                            style = AIKTheme.typography.h6
                        )
                    }
                }
                if (gifUrl.pm25GifUrl != null) {
                    GlideImage(
                        model = gifUrl.pm25GifUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreviewSuccess() {
    val homeUiState =
        HomeUiState.Success(
            location = Location(
                `do` = "Gangwon-do",
                sigungu = "Gangneung-si",
                eupmyeondong = "Gangdong-myeon",
                station = "옥천동"
            ),
            dataTime = "Saturday 12/25/2022 9:10 PM ",
            airLevel = AirLevel.Level1,
            detailAirData =
            DetailAirData(
                pm25Level = AirLevel.Level5,
                pm25Value = "25",
                pm10Level = AirLevel.Level4,
                pm10Value = "69",
                no2Level = AirLevel.Level2,
                no2Value = "0.011",
                so2Level = AirLevel.Level2,
                so2Value = "0.003",
                coLevel = AirLevel.Level5,
                coValue = "0.5",
                o3Level = AirLevel.Level1,
                o3Value = "0.049"
            ),
            dailyForecast = listOf(
                DailyForecast(
                    date = "2023-04-28",
                    airLevel = AirLevel.Level1
                ),
                DailyForecast(
                    date = "2023-04-29",
                    airLevel = AirLevel.Level1
                ),
                DailyForecast(
                    date = "2023-04-30",
                    airLevel = AirLevel.Level1
                ),
                DailyForecast(
                    date = "2023-05-01",
                    airLevel = AirLevel.Level5
                ),
                DailyForecast(
                    date = "2023-05-02",
                    airLevel = AirLevel.Level1
                ),
                DailyForecast(
                    date = "2023-05-03",
                    airLevel = AirLevel.Level1
                ),
                DailyForecast(
                    date = "2023-05-04",
                    airLevel = AirLevel.Level1
                )
            ),
            information =
            "The air quality will be generally 'average' due to the smooth air diffusion and precipitation, but the concentration is expected to be somewhat high in most western areas due to the inflow of foreign fine dust at night.",
            forecastModelUrl = KoreaForecastModelGif(null, null)
        )
    HomeScreen(
        onRefresh = {},
        onManageLocationClick = {},
        onParticulateMatterInfoClick = {},
        onAppInfoClick = {},
        onDrawerLocationClick = {},
        homeUiState = homeUiState,
        drawerUiState = DrawerUiState.Loading
    )
}


@Preview
@Composable
fun HomeScreenPreviewEmptyData() {
    val homeUiState = HomeUiState.Success()
    AIKTheme(airLevel = AirLevel.Level1) {
        HomeScreen(
            onRefresh = {},
            onManageLocationClick = {},
            onParticulateMatterInfoClick = {},
            onAppInfoClick = {},
            onDrawerLocationClick = {},
            homeUiState = homeUiState,
            drawerUiState = DrawerUiState.Loading
        )
    }
}

private fun AirLevel.mapToColor(): Color =
    when (this) {
        AirLevel.Level1 -> level1_core
        AirLevel.Level2 -> level2_core
        AirLevel.Level3 -> level3_core
        AirLevel.Level4 -> level4_core
        AirLevel.Level5 -> level5_core
        AirLevel.Level6 -> level6_core
        AirLevel.LevelError -> Color.Transparent
    }