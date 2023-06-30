package com.phil.airinkorea.ui.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.phil.airinkorea.data.model.AppGuide
import com.phil.airinkorea.data.repository.AppGuideRepository
import com.phil.airinkorea.ui.appguide.AppGuideContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


sealed interface AppGuideUiState {
    object Loading : AppGuideUiState
    data class Success(
        val appGuide: AppGuide
    ) : AppGuideUiState
}

@HiltViewModel
class AppGuideViewModel @Inject constructor(
    private val appGuideRepository: AppGuideRepository
) : ViewModel() {
    var appGuideUiState: MutableState<AppGuideUiState> = mutableStateOf(AppGuideUiState.Loading)
        private set
    init {
        getAppGuideData()
    }

    private fun getAppGuideData() {
        appGuideUiState.value = AppGuideUiState.Success(
            appGuide = appGuideRepository.getAppGuideData()
        )
    }
}