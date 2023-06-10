package com.phil.airinkorea.ui.addlocation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phil.airinkorea.R
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.ui.commoncomponent.CommonTopAppBar
import com.phil.airinkorea.ui.modifier.addFocusCleaner
import com.phil.airinkorea.ui.theme.*
import com.phil.airinkorea.ui.theme.icon.AIKIcons
import com.phil.airinkorea.ui.viewmodel.AddLocationUiState
import kotlinx.coroutines.launch

@Composable
fun AddLocationScreen(
    addLocationUiState: AddLocationUiState,
    onBackButtonClick: () -> Unit,
    onSearchTextChange: (TextFieldValue) -> Unit,
    onDialogConfirmButtonClick: (Location) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val systemUiController = rememberSystemUiController()
    var dialogOpen by remember {
        mutableStateOf(false)
    }
    var dialogLocation: Location? by remember {
        mutableStateOf(null)
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = common_background
        )
    }
    Scaffold(
        topBar = {
            CommonTopAppBar(
                onBackButtonClick = onBackButtonClick,
                title = stringResource(id = R.string.add_location),
                modifier = Modifier.statusBarsPadding()
            )
        },
        backgroundColor = common_background,
        modifier = Modifier
            .addFocusCleaner(focusManager)
            .systemBarsPadding()
            .imePadding()
    ) { innerPadding ->
        if (dialogOpen && (dialogLocation != null)) {
            AddLocationDialog(
                onConfirmButtonClick = onDialogConfirmButtonClick,
                onDismissRequest = { dialogOpen = false },
                location = dialogLocation!!
            )
        }
        AddLocationContent(
            modifier = Modifier.padding(innerPadding),
            addLocationUiState = addLocationUiState,
            onSearchTextChange = onSearchTextChange,
            onAddButtonClick = { location ->
                dialogLocation = location
                dialogOpen = true
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddLocationContent(
    modifier: Modifier = Modifier,
    addLocationUiState: AddLocationUiState,
    onSearchTextChange: (TextFieldValue) -> Unit,
    onAddButtonClick: (Location) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember {
        FocusRequester()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        //search
        OutlinedTextField(
            value = text,
            onValueChange = { value ->
                text = value
                onSearchTextChange(value)
            },
            textStyle = MaterialTheme.typography.body1,
            label = { Text(text = stringResource(id = R.string.search_location)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = AIKIcons.Search),
                    tint = Color.Black,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                IconButton(onClick = { text = TextFieldValue("") }) {
                    Icon(
                        painter = painterResource(id = AIKIcons.Cancel),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = common_background,
                cursorColor = Color.Black,
                focusedBorderColor = level1_core,
                focusedLabelColor = level1_core,
                unfocusedBorderColor = level1_on_core_container_subtext,
                unfocusedLabelColor = level1_on_core_container_subtext
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .focusRequester(focusRequester)
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }
        )

        Spacer(modifier = Modifier.size(10.dp))

        //search result list
        SearchResultList(
            searchResultList = addLocationUiState.searchResult,
            onAddButtonClick = onAddButtonClick,
        )
    }
}

@Composable
fun SearchResultList(
    searchResultList: List<Location>,
    onAddButtonClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Bottom,
        contentPadding = PaddingValues(vertical = 10.dp),
        modifier = modifier
    ) {
        items(searchResultList) { location ->
            SearchResultItem(onAddButtonClick = onAddButtonClick, location = location)
            Divider(color = divider, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    onAddButtonClick: (Location) -> Unit,
    location: Location
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(
                text = location.eupmyeondong,
                maxLines = 1,
                style = AIKTypography.subtitle2,
                textAlign = TextAlign.Center
            )
            Text(
                text = location.sigungu,
                maxLines = 1,
                style = AIKTypography.subtitle2,
                textAlign = TextAlign.Center
            )
            Text(
                text = location.`do`,
                maxLines = 1,
                style = AIKTypography.subtitle2,
                textAlign = TextAlign.Center
            )
        }

        IconButton(onClick = {
            onAddButtonClick(location)
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
        }
    }
}

@Composable
fun AddLocationDialog(
    onConfirmButtonClick: (Location) -> Unit,
    onDismissRequest: () -> Unit,
    location: Location
) {
    AlertDialog(
        backgroundColor = common_background,
        shape = MaterialTheme.shapes.medium,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.add_location),
                style = MaterialTheme.typography.h5,
                color = level1_on_core_container
            )
        },
        text = {
            Text(
                text = stringResource(
                    id = R.string.add_location_dialog_text,
                    location.eupmyeondong
                ),
                style = MaterialTheme.typography.body1,
                color = level1_on_core_container
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButtonClick(location)
                onDismissRequest()
            }) {
                Text(
                    text = stringResource(id = R.string.yes),
                    style = MaterialTheme.typography.subtitle1,
                    color = level1_on_core_container
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(id = R.string.no),
                    style = MaterialTheme.typography.subtitle1,
                    color = level1_on_core_container
                )
            }
        }
    )
}


@Preview
@Composable
fun AddLocationContentPreview() {
    AddLocationScreen(
        onSearchTextChange = {},
        onBackButtonClick = {},
        onDialogConfirmButtonClick = {},
        addLocationUiState = AddLocationUiState(
            searchResult = listOf(
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "namsang"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "namsang"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "namsang"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "namsang"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "namsang"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "namsang"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "namsang"
                )
            )
        )
    )
}