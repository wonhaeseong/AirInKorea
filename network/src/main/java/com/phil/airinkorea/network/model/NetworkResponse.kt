package com.phil.airinkorea.network.model
import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse<T: NetworkItem>(
    val response: Response<T>
)

@Serializable
data class Response<T: NetworkItem>(
    val body: NetworkResultBody<T>,
    val header: Header
)

@Serializable
data class Header(
    val resultMsg: String,
    val resultCode: String
)

@Serializable
data class NetworkResultBody<T: NetworkItem>(
    val totalCount: Int,
    val items: List<T>,
    val pageNo: Int,
    val numOfRows: Int
)
