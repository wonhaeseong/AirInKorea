package com.phil.airinkorea.data.model

sealed class Page {
    object GPS : Page()
    object Bookmark : Page()
    data class CustomLocation(val pageNum: Int) : Page()
}