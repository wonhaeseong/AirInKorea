package com.phil.airinkorea.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAirData(
    val forecast: ArrayList<NetworkForecastItem>,
    @SerialName("airData") val detailAirData: NetworkDetailAirData,
    @SerialName("pm10ImgUrl") val pm10GifUrl: String,
    @SerialName("pm25ImgUrl") val pm25GifUrl: String,
    @SerialName("thumbnailImageUrl") val koreaForecastMapImgUrl: String,
    val information: String
)

@Serializable
data class NetworkForecastItem(
    @SerialName("day") val date: String,
    @SerialName("forecastLevel") val airLevel: String
)

@Serializable
data class NetworkDetailAirData(
    val pm10Value: String?,
    val pm25Value: String?,
    val so2Value: String?,
    val coValue: String?,
    val o3Value: String?,
    val no2Value: String?,
    val khaiValue: String?,
    val pm10Grade: String?,
    val pm25Grade: String?,
    val so2Grade: String?,
    val coGrade: String?,
    val khaiGrade: String?,
    val no2Grade: String?,
    val o3Grade: String?,
    val pm10Flag: String?,
    val pm25Flag: String?,
    val no2Flag: String?,
    val o3Flag: String?,
    val so2Flag: String?,
    val coFlag: String?,
    val dataTime: String?,
    val sidoName: String?,
    val airLevel: String
)

@Serializable
data class NetworkLocation(
    @SerialName("en_do") val `do`: String,
    @SerialName("en_sigungu") val sigungu: String,
    @SerialName("en_eupmyeondong") val eupmyeondong: String,
    @SerialName("station") val station: String
)

@Serializable
data class NetworkCoordinateResult(
    @SerialName("location")val networkLocation: NetworkLocation,
    @SerialName("airData")val networkAirData: NetworkAirData
)
