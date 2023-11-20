package com.phil.airinkorea.data.repository

import android.net.Uri
import com.phil.airinkorea.data.model.DeveloperInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor() : AppInfoRepository {
    private val firstName = "Won"
    private val lastName = "HaeSeong"
    private val email = "gotjd8607@gmail.com"
    private val github = Uri.parse("https://github.com/want8607")
    private val nationality = "South Korea"
    override fun getDeveloperInfoStream(): Flow<DeveloperInfo> = flow {
        emit(
            DeveloperInfo(
                firstName = firstName,
                lastName = lastName,
                email = email,
                github = github,
                nationality = nationality
            )
        )
    }
}