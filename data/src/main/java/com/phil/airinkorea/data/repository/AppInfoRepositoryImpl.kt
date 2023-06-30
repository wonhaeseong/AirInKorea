package com.phil.airinkorea.data.repository

import android.net.Uri
import com.phil.airinkorea.data.model.DeveloperInfo
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor() : AppInfoRepository {
    private val firstName = "Won"
    private val lastName = "HaeSeong"
    private val email = "gotjd8607@gmail.com"
    private val github = Uri.parse("https://github.com/want8607")
    private val nationality = "South Korea"
    override fun getDeveloperInfo(): DeveloperInfo = DeveloperInfo(
        firstName = firstName,
        lastName = lastName,
        email = email,
        github = github,
        nationality = nationality
    )
}