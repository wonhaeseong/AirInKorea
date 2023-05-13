package com.phil.airinkorea.domain.usecases.user

import com.phil.airinkorea.domain.model.Mode
import com.phil.airinkorea.domain.repository.AppStatusRepository
import javax.inject.Inject

class FetchDefaultModeUseCase @Inject constructor(
    private val appStatusRepository: AppStatusRepository
) {
    suspend operator fun invoke(mode: Mode) {
        appStatusRepository.fetchDefaultMode(mode)
    }
}