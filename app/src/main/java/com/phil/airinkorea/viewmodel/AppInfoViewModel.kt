package com.phil.airinkorea.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phil.airinkorea.data.model.DeveloperInfo
import com.phil.airinkorea.data.repository.AppInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class AppInfoUiState(
    val developerInfo: DeveloperInfo? = null
)

@HiltViewModel
class AppInfoViewModel @Inject constructor(
    appInfoRepository: AppInfoRepository
) : ViewModel() {
    val appInfoUiState =
        appInfoRepository.getDeveloperInfoStream().map { AppInfoUiState(it) }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), AppInfoUiState()
        )
}