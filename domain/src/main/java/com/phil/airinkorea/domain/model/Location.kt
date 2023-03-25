package com.phil.airinkorea.domain.model


/**
 * 위치 정보를 갖는 데이터 클래스
 * @param do 도
 * @param sigungu 시군구
 * @param eupmyeondong 읍면동
 */
data class Location(val `do`: String, val sigungu: String, val eupmyeondong: String)
data class LocationAndStation(
    val `do`: String,
    val sigungu: String,
    val eupmyeondong: String,
    val station: String
)
