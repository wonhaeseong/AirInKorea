package com.phil.airinkorea.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import com.phil.airinkorea.ui.theme.*

@Preview("background")
@Composable
fun AikBackground(
    modifier: Modifier = Modifier
        .background(level1_background)
) {
   Text(
       text = "ddd",
       color = level1_core,
       modifier = modifier
           .fillMaxSize()
   )
}