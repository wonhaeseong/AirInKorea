package com.phil.airinkorea.ui.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.phil.airinkorea.ui.appguide.AppGuideContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject




sealed interface AppGuideUiState {
    object Loading : AppGuideUiState
//    data class Success(
//    ) : AppGuideUiState
}

@HiltViewModel
class AppGuideViewModel @Inject constructor(

) : ViewModel() {
    val appGuideUiState: MutableState<AppGuideUiState> = mutableStateOf(AppGuideUiState.Loading)
}