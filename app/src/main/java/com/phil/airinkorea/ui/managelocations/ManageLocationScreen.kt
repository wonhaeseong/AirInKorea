package com.phil.airinkorea.ui.managelocations

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.ui.commoncomponent.CommonTopAppBar
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.bookmark
import com.phil.airinkorea.ui.theme.common_background
import com.phil.airinkorea.ui.theme.divider
import com.phil.airinkorea.ui.theme.icon.AIKIcons
import com.phil.airinkorea.ui.theme.manage_location_button
import com.phil.airinkorea.ui.theme.manage_location_core
import com.phil.airinkorea.ui.theme.manage_location_core_container
import com.phil.airinkorea.ui.theme.manage_location_on_core_container
import com.phil.airinkorea.ui.theme.unselected_bookmark
import com.phil.airinkorea.viewmodel.ManageLocationUiState

@Composable
fun ManageLocationScreen(
    onBackButtonClick: () -> Unit,
    onBookmarkDialogConfirmButtonClick: (Location) -> Unit,
    onDeleteDialogConfirmButtonClick: (Location) -> Unit,
    onAddLocationButtonClick: () -> Unit,
    manageLocationUiState: ManageLocationUiState
) {
    var deleteDialogOpen by remember {
        mutableStateOf(false)
    }
    var changeBookmarkDialogOpen by remember {
        mutableStateOf(false)
    }
    var dialogLocation: Location? by remember {
        mutableStateOf(null)
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                onBackButtonClick = onBackButtonClick,
                title = stringResource(id = R.string.manage_locations),
                modifier = Modifier.statusBarsPadding()
            )
        },
        backgroundColor = common_background
    ) { innerPadding ->
        if (changeBookmarkDialogOpen && dialogLocation != null) {
            ManageLocationChangeBookmarkDialog(
                onConfirmButtonClick = onBookmarkDialogConfirmButtonClick,
                onDismissRequest = { changeBookmarkDialogOpen = false },
                location = dialogLocation!!,
            )
        }

        if (deleteDialogOpen && dialogLocation != null) {
            ManageLocationDeleteLocationDialog(
                onConfirmButtonClick = onDeleteDialogConfirmButtonClick,
                onDismissRequest = { deleteDialogOpen = false },
                location = dialogLocation!!
            )
        }
        ManageLocationContent(
            onBookmarkButtonClick = { location ->
                dialogLocation = location
                changeBookmarkDialogOpen = true
            },
            onLocationDeleteButtonClick = {
                dialogLocation = it
                deleteDialogOpen = true
            },
            onAddLocationButtonClick = onAddLocationButtonClick,
            manageLocationUiState = manageLocationUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ManageLocationContent(
    manageLocationUiState: ManageLocationUiState,
    onBookmarkButtonClick: (Location) -> Unit,
    onLocationDeleteButtonClick: (Location) -> Unit,
    onAddLocationButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Spacer(modifier = Modifier.size(5.dp))
        //Add Location Button
        TextButton(
            shape = AIKTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(manage_location_button),
            onClick = onAddLocationButtonClick,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.add_location),
                style = AIKTheme.typography.button,
                color = manage_location_core_container
            )
        }
        //bookmark
        ManageLocationsBookmark(
            bookmarkedLocation = manageLocationUiState.bookmark,
            onLocationDeleteButtonClick = onLocationDeleteButtonClick,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Divider(modifier = Modifier.fillMaxWidth(), color = divider, thickness = 1.dp)
        //Location List
        ManageLocationsLocationList(
            locationData = manageLocationUiState.userLocationList,
            onBookmarkButtonClick = onBookmarkButtonClick,
            onLocationDeleteButtonClick = onLocationDeleteButtonClick
        )
    }
}

@Composable
fun ManageLocationsBookmark(
    bookmarkedLocation: Location?,
    onLocationDeleteButtonClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.bookmark),
            style = AIKTheme.typography.subtitle1
        )
        if (bookmarkedLocation == null) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = stringResource(id = R.string.bookmark_is_not_set),
                style = AIKTheme.typography.body1
            )
            Spacer(modifier = Modifier.size(10.dp))
        } else {
            Spacer(modifier = Modifier.size(10.dp))
            ManageLocationsItem(
                isBookmarked = true,
                location = bookmarkedLocation,
                onLocationDeleteButtonClick = onLocationDeleteButtonClick,
            )
        }
    }
}

@Composable
fun ManageLocationsLocationList(
    locationData: List<Location>,
    onBookmarkButtonClick: (Location) -> Unit,
    onLocationDeleteButtonClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.my_locations),
            style = AIKTheme.typography.subtitle1
        )
        if (locationData.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
                modifier = modifier
            ) {
                items(locationData) { location ->
                    ManageLocationsItem(
                        isBookmarked = false,
                        location = location,
                        onBookmarkButtonClick = onBookmarkButtonClick,
                        onLocationDeleteButtonClick = onLocationDeleteButtonClick
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = stringResource(id = R.string.please_add_a_location),
                style = AIKTheme.typography.body1
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@Composable
fun ManageLocationsItem(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean,
    location: Location,
    onBookmarkButtonClick: (Location) -> Unit = {},
    onLocationDeleteButtonClick: (Location) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = manage_location_core,
                shape = AIKTheme.shapes.small
            )
            .padding(horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
                .weight(1f)
        ) {
            IconButton(
                onClick = {
                    onBookmarkButtonClick(location)
                },
                enabled = !isBookmarked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    tint = if (isBookmarked) {
                        bookmark
                    } else {
                        unselected_bookmark
                    }
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 30.dp)
            ) {
                Text(
                    text = location.eupmyeondong,
                    maxLines = 1,
                    style = AIKTheme.typography.subtitle2,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = location.sigungu,
                    maxLines = 1,
                    style = AIKTheme.typography.subtitle2,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = location.`do`,
                    maxLines = 1,
                    style = AIKTheme.typography.subtitle2,
                    textAlign = TextAlign.Center
                )
            }
        }
        if (!isBookmarked) {
            IconButton(onClick = { onLocationDeleteButtonClick(location) }) {
                Icon(painter = painterResource(id = AIKIcons.TrashCan), contentDescription = null)
            }
        }
    }
}

@Composable
fun ManageLocationChangeBookmarkDialog(
    onConfirmButtonClick: (Location) -> Unit,
    onDismissRequest: () -> Unit,
    location: Location
) {
    AlertDialog(
        backgroundColor = common_background,
        shape = AIKTheme.shapes.medium,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.change_bookmark),
                style = AIKTheme.typography.h5,
                color = manage_location_on_core_container
            )
        },
        text = {
            Text(
                text = stringResource(
                    id = R.string.change_bookmark_dialog_text,
                    location.eupmyeondong
                ),
                style = AIKTheme.typography.body1,
                color = manage_location_on_core_container
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButtonClick(location)
                onDismissRequest()
            }) {
                Text(
                    text = stringResource(id = R.string.yes),
                    style = AIKTheme.typography.subtitle1,
                    color = manage_location_on_core_container
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(id = R.string.no),
                    style = AIKTheme.typography.subtitle1,
                    color = manage_location_on_core_container
                )
            }
        }
    )
}

@Composable
fun ManageLocationDeleteLocationDialog(
    onConfirmButtonClick: (Location) -> Unit,
    onDismissRequest: () -> Unit,
    location: Location
) {
    AlertDialog(
        backgroundColor = common_background,
        shape = AIKTheme.shapes.medium,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.delete_location),
                style = AIKTheme.typography.h5,
                color = manage_location_on_core_container
            )
        },
        text = {
            Text(
                text = stringResource(
                    id = R.string.delete_location_dialog_text,
                    location.eupmyeondong
                ),
                style = AIKTheme.typography.body1,
                color = manage_location_on_core_container
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButtonClick(location)
                onDismissRequest()
            }) {
                Text(
                    text = stringResource(id = R.string.yes),
                    style = AIKTheme.typography.subtitle1,
                    color = manage_location_on_core_container
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(id = R.string.no),
                    style = AIKTheme.typography.subtitle1,
                    color = manage_location_on_core_container
                )
            }
        }
    )
}

@Preview
@Composable
fun ManageLocationScreenPreviewSuccess() {
    ManageLocationScreen(
        onBackButtonClick = {},
        onBookmarkDialogConfirmButtonClick = {},
        onDeleteDialogConfirmButtonClick = {},
        onAddLocationButtonClick = {},
        manageLocationUiState = ManageLocationUiState(
            bookmark = Location(
                `do` = "Gyeongsangnam-do",
                sigungu = "Hamyang-gun",
                eupmyeondong = "Anui-myeon",
                station = "Dd"
            ),
            userLocationList =
            listOf(
                Location(
                    `do` = "abc",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "Dd"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "Dd"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "Dd"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "Dd"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon",
                    station = "Dd"
                )
            )
        )
    )
}

@Preview
@Composable
fun ManageLocationScreenPreviewEmpty() {
    ManageLocationScreen(
        onBackButtonClick = {},
        onBookmarkDialogConfirmButtonClick = {},
        onDeleteDialogConfirmButtonClick = {},
        onAddLocationButtonClick = {},
        manageLocationUiState = ManageLocationUiState(
            bookmark = Location(
                `do` = "Gyeongsangnam-do",
                sigungu = "Hamyang-gun",
                eupmyeondong = "Anui-myeon",
                station = "Dd"
            )
        )
    )
}
