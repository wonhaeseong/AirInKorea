package com.phil.airinkorea.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phil.airinkorea.domain.model.AirLevel

private val level1Color = AIKColors(
    core = level1_core,
    on_core = level1_on_core,
    core_container = level1_core_container,
    on_core_container = level1_on_core_container,
    on_core_container_subtext = level1_on_core_container_subtext,
    core_button = level1_button,
    core_background = level1_background
)

private val level2Color = AIKColors(
    core = level2_core,
    on_core = level2_on_core,
    core_container = level2_core_container,
    on_core_container = level2_on_core_container,
    on_core_container_subtext = level2_on_core_container_subtext,
    core_button = level2_button,
    core_background = level2_background
)

private val level3Color = AIKColors(
    core = level3_core,
    on_core = level3_on_core,
    core_container = level3_core_container,
    on_core_container = level3_on_core_container,
    on_core_container_subtext = level3_on_core_container_subtext,
    core_button = level3_button,
    core_background = level3_background
)

private val level4Color = AIKColors(
    core = level4_core,
    on_core = level4_on_core,
    core_container = level4_core_container,
    on_core_container = level4_on_core_container,
    on_core_container_subtext = level4_on_core_container_subtext,
    core_button = level4_button,
    core_background = level4_background
)

private val level5Color = AIKColors(
    core = level5_core,
    on_core = level5_on_core,
    core_container = level5_core_container,
    on_core_container = level5_on_core_container,
    on_core_container_subtext = level5_on_core_container_subtext,
    core_button = level5_button,
    core_background = level5_background
)

private val level6Color = AIKColors(
    core = level6_core,
    on_core = level6_on_core,
    core_container = level6_core_container,
    on_core_container = level6_on_core_container,
    on_core_container_subtext = level6_on_core_container_subtext,
    core_button = level6_button,
    core_background = level6_background
)

private val levelErrorColor = AIKColors(
    core = level6_core,
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
    on_core: Color,
    core_container: Color,
    on_core_container: Color,
    on_core_container_subtext: Color,
    core_button: Color,
    core_background: Brush
) {
    var core by mutableStateOf(core)
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

    fun update(other: AIKColors) {
        core = other.core
        on_core = other.on_core
        core_container = other.core_container
        on_core_container = other.on_core_container
        on_core_container_subtext = other.on_core_container_subtext
        core_button = other.core_button
        core_background = other.core_background
    }

    fun copy(): AIKColors = AIKColors(
        core = core,
        on_core = on_core,
        core_container = core_container,
        on_core_container = on_core_container,
        on_core_container_subtext = on_core_container_subtext,
        core_button = core_button,
        core_background = core_background
    )
}

@Composable
fun ProvideAIKColors(
    colors: AIKColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalAIKColors provides colorPalette, content = content)
}

private val LocalAIKColors = staticCompositionLocalOf<AIKColors>{
    error("No AIKColorPalette provided")
}

object AIKTheme {
    val colors: AIKColors
        @Composable
        get() = LocalAIKColors.current
}

@Composable
fun AIKTheme(
    airLevel: AirLevel,
    content: @Composable () -> Unit
){
    val colors = when(airLevel){
        AirLevel.Level1 -> level1Color
        AirLevel.Level2 -> level2Color
        AirLevel.Level3 -> level3Color
        AirLevel.Level4 -> level4Color
        AirLevel.Level5 -> level5Color
        AirLevel.Level6 -> level6Color
        AirLevel.LevelError -> levelErrorColor
    }

    val sysUicController = rememberSystemUiController()
    SideEffect {
        sysUicController.setStatusBarColor(
            color = Color.Transparent
        )
    }

    ProvideAIKColors(colors = colors) {
        MaterialTheme(
            colors = debugColors(),
            typography = AIKTypography,
            shapes = Shapes,
            content = content
        )
    }
}

fun debugColors(
    debugColor: Color = Color.Transparent
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = true
)