package com.phil.airinkorea.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.AppGuide
import com.phil.airinkorea.data.repository.AppGuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class AppGuideUiState(
    val appGuide: AppGuide? = null
)

@HiltViewModel
class AppGuideViewModel @Inject constructor(
    appGuideRepository: AppGuideRepository
) : ViewModel() {
    val appGuideUiState: StateFlow<AppGuideUiState> =
        appGuideRepository.getAppGuideDataStream().map {
            AppGuideUiState(it)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            AppGuideUiState()
        )
}