package com.phil.airinkorea.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.DeveloperInfo
import com.phil.airinkorea.data.repository.AppInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AppInfoUiState {
    object Loading : AppInfoUiState
    data class Success(
        val developerInfo: DeveloperInfo
    ) : AppInfoUiState
}

sealed interface AppInfoScreenActivityEvent {
    object ShowOpenSourceLicenses : AppInfoScreenActivityEvent
    data class ShowGithubInBrowser(val uri: Uri) : AppInfoScreenActivityEvent
}

@HiltViewModel
class AppInfoViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository
) : ViewModel() {

    var appInfoUiState: MutableState<AppInfoUiState> = mutableStateOf(AppInfoUiState.Loading)
        private set

    private val _activityEvent = MutableSharedFlow<AppInfoScreenActivityEvent>()
    val activityEvent: SharedFlow<AppInfoScreenActivityEvent> = _activityEvent.asSharedFlow()

    init {
        getAppInfoData()
    }

    private fun getAppInfoData() {
        appInfoUiState.value =
            AppInfoUiState.Success(
                developerInfo = appInfoRepository.getDeveloperInfo()
            )
    }

    fun showOpenSourceLicenses() {
        viewModelScope.launch(Dispatchers.IO) {
            _activityEvent.emit(AppInfoScreenActivityEvent.ShowOpenSourceLicenses)
        }
    }

    fun showGithubInBrowser(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _activityEvent.emit(AppInfoScreenActivityEvent.ShowGithubInBrowser(uri = uri))
        }
    }
}