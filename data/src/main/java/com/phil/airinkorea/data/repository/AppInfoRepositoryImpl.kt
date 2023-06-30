package com.phil.airinkorea.data.repository

import android.net.Uri
import com.phil.airinkorea.data.model.DeveloperInfo
import javax.inject.Inject

class AppInfoRepositoryImpl @Inject constructor() : AppInfoRepository {
    private val firstName = "Won"
    private val lastName = "HaeSeong"
    private val github = Uri.parse("https://github.com/want8607")
    private val nationality = "South Korea"
    override fun getDeveloperInfo(): DeveloperInfo = DeveloperInfo(
        firstName = firstName,
        lastName = lastName,
        github = github,
        nationality = nationality
    )
}