package com.phil.airinkorea.data.model

import android.net.Uri

data class DeveloperInfo(
    val firstName: String,
    val lastName: String,
    val email: String,
    val github: Uri,
    val nationality: String
)
