package com.phil.airinkorea.ui.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.theme.AIKTheme
import com.phil.airinkorea.ui.theme.PollutionLevel

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

@Preview
@Composable
fun DrawerItemPreview() {
    AIKTheme(PollutionLevel.EXCELLENT) {
        DrawerItem(text = "Item")
    }
}