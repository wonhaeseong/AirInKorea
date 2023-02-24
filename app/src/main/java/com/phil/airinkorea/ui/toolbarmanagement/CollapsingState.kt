package com.phil.airinkorea.ui.toolbarmanagement

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.ui.tooling.preview.Preview


class CollapsingState(heightRange: IntRange, scrollValue: Int = 0) : ToolbarState {

    val maxHeight = heightRange.last
    val minHeight = heightRange.first
    val rangeDifference = heightRange.last - heightRange.first

    override val height: Float
        get() = (maxHeight.toFloat() - (scrollValue.toFloat())).coerceIn(
            minHeight.toFloat(),
            maxHeight.toFloat()
        )

    override val progress: Float
        get() = 1 - (maxHeight - height) / rangeDifference


    var _scrollValue by mutableStateOf(
        value = scrollValue.coerceAtLeast(0),
        policy = structuralEqualityPolicy()
    )

    override var scrollValue: Int
        get() = _scrollValue
        set(value) {
            _scrollValue = value.coerceAtLeast(0)
        }

    companion object {
        val Saver = run {
            val minHeightKey = "MinHeight"
            val maxHeightKey = "MaxHeight"
            val scrollValueKey = "ScrollValue"

            mapSaver(
                save = {
                    mapOf(
                        minHeightKey to it.minHeight,
                        maxHeightKey to it.maxHeight,
                        scrollValueKey to it.scrollValue
                    )
                },
                restore = {
                    CollapsingState(
                        heightRange = (it[minHeightKey] as Int)..(it[maxHeightKey] as Int),
                        scrollValue = it[scrollValueKey] as Int
                    )
                }
            )
        }
    }
}