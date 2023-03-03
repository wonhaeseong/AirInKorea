package com.phil.airinkorea.ui.managelocations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.model.Location
import com.phil.airinkorea.ui.commoncomponent.CommonTopAppBar
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.theme.*

@Composable
fun ManageLocationScreen(
    onBackButtonClick: () -> Unit,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onLocationDeleteButtonClick: (Location) -> Unit,
    onAddLocationButtonClick: () -> Unit,
    bookmarkedLocation: Location,
    locations: List<Location>
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(
                onBackButtonClick = onBackButtonClick,
                title = stringResource(id = R.string.manage_locations)
            )
        },
        backgroundColor = common_background
    ) { innerPadding ->
        ManageLocationContent(
            bookmarkedLocation = bookmarkedLocation,
            onBookmarkButtonClick = onBookmarkButtonClick,
            onLocationDeleteButtonClick = onLocationDeleteButtonClick,
            onAddLocationButtonClick = onAddLocationButtonClick,
            locations = locations,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ManageLocationContent(
    bookmarkedLocation: Location,
    locations: List<Location>,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onLocationDeleteButtonClick: (Location) -> Unit,
    onAddLocationButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        //Add Location Button
        Button(
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(AIKTheme.colors.core_button),
            onClick = onAddLocationButtonClick,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(
                text = stringResource(id = R.string.add_location),
                style = MaterialTheme.typography.button,
                color = AIKTheme.colors.core_container
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        //bookmark
        ManageLocationsBookmark(
            bookmarkedLocation = bookmarkedLocation,
            onBookmarkButtonClick = onBookmarkButtonClick,
            onLocationDeleteButtonClick = onLocationDeleteButtonClick,
        )
        Divider(modifier = Modifier.fillMaxWidth(), color = divider, thickness = 1.dp)
        //Location List
        ManageLocationsLocationList(
            locations = locations,
            onBookmarkButtonClick = onBookmarkButtonClick,
            onLocationDeleteButtonClick = onLocationDeleteButtonClick
        )
    }

}

@Composable
fun ManageLocationsBookmark(
    bookmarkedLocation: Location,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onLocationDeleteButtonClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(text = stringResource(id = R.string.bookmark), style = MaterialTheme.typography.subtitle1)
        Spacer(modifier = Modifier.size(10.dp))
        ManageLocationsItem(
            isBookmarked = true,
            location = bookmarkedLocation,
            onBookmarkButtonClick = onBookmarkButtonClick,
            onLocationDeleteButtonClick = onLocationDeleteButtonClick,
        )
    }
}

@Composable
fun ManageLocationsLocationList(
    locations: List<Location>,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onLocationDeleteButtonClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        modifier = modifier
    ) {
        items(locations) { location ->
            ManageLocationsItem(
                isBookmarked = false,
                location = location,
                onBookmarkButtonClick = onBookmarkButtonClick,
                onLocationDeleteButtonClick = onLocationDeleteButtonClick
            )
        }
    }
}

@Composable
fun ManageLocationsItem(
    isBookmarked: Boolean,
    location: Location,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onLocationDeleteButtonClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = AIKTheme.colors.core,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        IconButton(onClick = { onBookmarkButtonClick(isBookmarked) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = if (isBookmarked) {
                    bookmark
                }else{
                    unselected_bookmark
                }
            )
        }
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(
                text = location.eupmyeondong,
                maxLines = 1,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Text(
                text = location.sigungu,
                maxLines = 1,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Text(
                text = location.`do`,
                maxLines = 1,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
        }
        IconButton(onClick = { onLocationDeleteButtonClick(location) }) {
            Icon(painter = painterResource(id = AIKIcons.TrashCan), contentDescription = null)
        }
    }
}

@Preview
@Composable
fun ManageLocationScreenPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.EXCELLENT) {
        ManageLocationScreen(
            onBackButtonClick = {},
            onBookmarkButtonClick = {},
            onLocationDeleteButtonClick = {},
            onAddLocationButtonClick = {},
            bookmarkedLocation = Location(
                `do` = "Gyeongsangnam-do",
                sigungu = "Hamyang-gun",
                eupmyeondong = "Anui-myeon"
            ),
            locations = listOf(
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon"
                ),
                Location(
                    `do` = "Gyeongsangnam-do",
                    sigungu = "Hamyang-gun",
                    eupmyeondong = "Anui-myeon"
                ),
            )
        )
    }
}
