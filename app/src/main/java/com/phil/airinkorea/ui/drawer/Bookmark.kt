package com.phil.airinkorea.ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel
import com.phil.airinkorea.ui.theme.bookmark

@Composable
fun Bookmark(
    modifier: Modifier = Modifier,
    bookmarkedLocation: String? = null
) {
    Column(modifier = modifier) {
        DrawerTitle(icon = painterResource(id = AIKIcons.Star), tint = bookmark, stringId = R.string.bookmark)
        if (bookmarkedLocation.isNullOrBlank()) {
            DrawerItem(text = stringResource(id = R.string.bookmark_is_not_set), itemEnable = false)
        } else {
            DrawerItem(text = bookmarkedLocation)
        }
    }
}

@Composable
@Preview
fun BookmarkPreview() {
    AIKTheme(pollutionLevel = PollutionLevel.EXCELLENT) {
        Bookmark(bookmarkedLocation = "Hamyang-eup")
    }
}
