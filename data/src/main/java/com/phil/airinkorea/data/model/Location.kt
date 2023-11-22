package com.phil.airinkorea.data.model


/**
 * 위치 정보를 갖는 데이터 클래스
 * @param do 도
 * @param sigungu 시군구
 * @param eupmyeondong 읍면동
 * @param station 측정소명
 */
data class Location(
    val `do`: String,
    val sigungu: String,
    val eupmyeondong: String,
    val station: String
)

//data class UserLocation(
//    val location: Location,
//    val isGPS: Boolean = false,
//    val isBookmark: Boolean = false,
//    val isSelected: Boolean = false
//)