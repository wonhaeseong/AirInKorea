package com.phil.airinkorea.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.phil.airinkorea.data.model.DeveloperInfo
import com.phil.airinkorea.data.repository.AppInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface AppInfoUiState {
    object Loading : AppInfoUiState
    data class Success(
        val developerInfo: DeveloperInfo
    ) : AppInfoUiState
}

@HiltViewModel
class AppInfoViewModel @Inject constructor(
    private val appInfoRepository: AppInfoRepository
) : ViewModel() {

    var appInfoUiState: MutableState<AppInfoUiState> = mutableStateOf(AppInfoUiState.Loading)
        private set

    init {
        Log.d("TAG","viewmodel 생성")
        getAppInfoData()
    }

    private fun getAppInfoData() {
        appInfoUiState.value =
            AppInfoUiState.Success(
                developerInfo = appInfoRepository.getDeveloperInfo()
            )
    }
}