package com.phil.airinkorea.ui.commoncomponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.theme.AIKTypography
import com.phil.airinkorea.ui.theme.level1_on_core_container

@Composable
fun CommonContentTitle(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            style = AIKTypography.subtitle1,
            color = level1_on_core_container
        )
    }
}