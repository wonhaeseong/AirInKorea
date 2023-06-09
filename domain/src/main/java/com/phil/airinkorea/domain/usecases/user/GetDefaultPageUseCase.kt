package com.phil.airinkorea.domain.usecases.user

import com.phil.airinkorea.data.repository.AppStatusRepository
import javax.inject.Inject

class GetDefaultPageUseCase @Inject constructor(
    private val appStatusRepository: com.phil.airinkorea.data.repository.AppStatusRepository
) {
    suspend operator fun invoke(): Int =
        appStatusRepository.getDefaultPage()
}