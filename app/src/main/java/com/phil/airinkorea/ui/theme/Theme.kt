package com.phil.airinkorea.ui.theme

import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.phil.airinkorea.data.model.AirLevel

private val level1Color = AIKColors(
    core = level1_core,
    core_20 = level1_core_20,
    on_core = level1_on_core,
    core_container = level1_core_container,
    on_core_container = level1_on_core_container,
    on_core_container_subtext = level1_on_core_container_subtext,
    core_button = level1_button,
    core_background = level1_background
)

private val level2Color = AIKColors(
    core = level2_core,
    core_20 = level2_core_20,
    on_core = level2_on_core,
    core_container = level2_core_container,
    on_core_container = level2_on_core_container,
    on_core_container_subtext = level2_on_core_container_subtext,
    core_button = level2_button,
    core_background = level2_background
)

private val level3Color = AIKColors(
    core = level3_core,
    core_20 = level3_core_20,
    on_core = level3_on_core,
    core_container = level3_core_container,
    on_core_container = level3_on_core_container,
    on_core_container_subtext = level3_on_core_container_subtext,
    core_button = level3_button,
    core_background = level3_background
)

private val level4Color = AIKColors(
    core = level4_core,
    core_20 = level4_core_20,
    on_core = level4_on_core,
    core_container = level4_core_container,
    on_core_container = level4_on_core_container,
    on_core_container_subtext = level4_on_core_container_subtext,
    core_button = level4_button,
    core_background = level4_background
)

private val level5Color = AIKColors(
    core = level5_core,
    core_20 = level5_core_20,
    on_core = level5_on_core,
    core_container = level5_core_container,
    on_core_container = level5_on_core_container,
    on_core_container_subtext = level5_on_core_container_subtext,
    core_button = level5_button,
    core_background = level5_background
)

private val level6Color = AIKColors(
    core = level6_core,
    core_20 = level6_core_20,
    on_core = level6_on_core,
    core_container = level6_core_container,
    on_core_container = level6_on_core_container,
    on_core_container_subtext = level6_on_core_container_subtext,
    core_button = level6_button,
    core_background = level6_background
)

private val levelErrorColor = AIKColors(
    core = level6_core,
    core_20 = level6_core_20,
    on_core = level6_on_core,
    core_container = level6_core_container,
    on_core_container = level6_on_core_container,
    on_core_container_subtext = level6_on_core_container_subtext,
    core_button = level6_button,
    core_background = level6_background
)

@Stable
class AIKColors(
    core: Color,
    core_20: Color,
    on_core: Color,
    core_container: Color,
    on_core_container: Color,
    on_core_container_subtext: Color,
    core_button: Color,
    core_background: Brush
) {
    var core by mutableStateOf(core)
        private set
    var core_20 by mutableStateOf(core_20)
        private set
    var on_core by mutableStateOf(on_core)
        private set
    var core_container by mutableStateOf(core_container)
        private set
    var on_core_container by mutableStateOf(on_core_container)
        private set
    var on_core_container_subtext by mutableStateOf(on_core_container_subtext)
        private set
    var core_button by mutableStateOf(core_button)
        private set
    var core_background by mutableStateOf(core_background)
        private set

//    fun update(other: AIKColors) {
//        core = other.core
//        core_20 = other.core_20
//        on_core = other.on_core
//        core_container = other.core_container
//        on_core_container = other.on_core_container
//        on_core_container_subtext = other.on_core_container_subtext
//        core_button = other.core_button
//        core_background = other.core_background
//    }
//
//    fun copy(): AIKColors = AIKColors(
//        core = core,
//        core_20 = core_20,
//        on_core = on_core,
//        core_container = core_container,
//        on_core_container = on_core_container,
//        on_core_container_subtext = on_core_container_subtext,
//        core_button = core_button,
//        core_background = core_background
//    )
}

private val LocalAIKColors = compositionLocalOf<AIKColors> {
    error("No AIKColor Provided")
}

object AIKTheme {
    val colors: AIKColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAIKColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = AIKTypography

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = Shapes
}

@Composable
fun AIKTheme(
    airLevel: AirLevel,
    content: @Composable () -> Unit
) {
    val colors = when (airLevel) {
        AirLevel.Level1 -> level1Color
        AirLevel.Level2 -> level2Color
        AirLevel.Level3 -> level3Color
        AirLevel.Level4 -> level4Color
        AirLevel.Level5 -> level5Color
        AirLevel.Level6 -> level6Color
        AirLevel.LevelError -> levelErrorColor
    }

//    val rememberedColors = remember {
//        // Explicitly creating a new object here so we don't mutate the initial [colors]
//        // provided, and overwrite the values set in it.
//        colors.copy()
//    }.apply { update(colors) }

    CompositionLocalProvider(
        LocalAIKColors provides colors,
        content = content
    )
}