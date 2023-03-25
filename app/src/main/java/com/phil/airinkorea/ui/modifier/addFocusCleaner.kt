package com.phil.airinkorea.ui.modifier

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.addFocusCleaner(
    focusManager: FocusManager,
): Modifier = pointerInput(Unit) {
    detectTapGestures(onTap = {
        focusManager.clearFocus()
    })
}