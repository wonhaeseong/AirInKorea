package com.phil.airinkorea.ui.commoncomponent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phil.airinkorea.ui.theme.AIKTypography
import com.phil.airinkorea.ui.theme.level1_on_core_container

@Composable
fun CommonExpendableContent(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    var expandedState by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expandedState = !expandedState }
            .padding(10.dp)
    ) {
        Text(
            text = title,
            style = AIKTypography.subtitle1,
            color = level1_on_core_container
        )
        AnimatedVisibility(visible = expandedState) {
            content()
        }
    }
}