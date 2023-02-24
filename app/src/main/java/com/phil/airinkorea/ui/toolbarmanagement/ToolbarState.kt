package com.phil.airinkorea.ui.toolbarmanagement

import androidx.compose.runtime.Stable

@Stable
interface ToolbarState {
    val height: Float
    val progress: Float
    var scrollValue: Int
}
