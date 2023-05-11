package com.phil.airinkorea.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.phil.airinkorea.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ManageLocationUiState(
    var bookmark: Location? = null,
    var userLocationList: List<Location> = emptyList()
)
@HiltViewModel
class ManageLocationViewModel @Inject constructor(

): ViewModel(){
}