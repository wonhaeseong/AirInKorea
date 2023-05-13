package com.phil.airinkorea.domain.usecases.user

import com.phil.airinkorea.domain.model.Mode
import com.phil.airinkorea.domain.repository.AppStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDefaultModeUseCase @Inject constructor(
    private val appStatusRepository: AppStatusRepository
) {
    operator fun invoke(): Flow<Mode> =
        appStatusRepository.getDefaultMode()
}