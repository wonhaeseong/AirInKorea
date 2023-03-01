package com.phil.airinkorea.ui.drawer

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.R
import com.phil.airinkorea.ui.icon.AIKIcons
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel

/**
 * @param icon painterResource에 전달할 resource Id
 * @param title 타이틀로 들어갈 String resource Id
 */
@Composable
fun DrawerTitle(
    modifier: Modifier = Modifier,
    icon: Painter,
    tint: Color = Color.Black,
    iconBackgroundColor : Color = Color.Transparent,
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
            modifier = Modifier.size(24.dp)
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

@Preview
@Composable
fun DrawerTitlePreview() {
    AIKTheme(PollutionLevel.EXCELLENT) {
        DrawerTitle(icon = painterResource(id = AIKIcons.Star), stringId = R.string.bookmark)
    }
}
