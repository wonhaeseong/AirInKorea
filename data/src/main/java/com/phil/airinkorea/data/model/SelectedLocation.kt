package com.phil.airinkorea.data.model

sealed class SelectedLocation{
    object GPS : SelectedLocation()
    object Bookmark : SelectedLocation()
    data class UserLocation(val num: Int) : SelectedLocation()
}