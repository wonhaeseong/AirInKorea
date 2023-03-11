package com.phil.airinkorea.domain.model

sealed class Result<R>{
    data class Success<T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}
