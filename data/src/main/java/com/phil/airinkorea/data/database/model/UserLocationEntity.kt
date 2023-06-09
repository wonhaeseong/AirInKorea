package com.phil.airinkorea.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.phil.airinkorea.data.model.Location

/**
 * 사용자가 추가한 위치 정보를 가지는 entity
 */
@Entity(
    tableName = "user_locations",
    primaryKeys = ["en_do", "en_sigungu", "en_eupmyeondong"]
)
data class UserLocationEntity(
    @ColumnInfo(name = "en_do") val enDo: String,
    @ColumnInfo(name = "en_sigungu") val enSigungu: String,
    @ColumnInfo(name = "en_eupmyeondong") val enEupmyeondong: String,
    @ColumnInfo(name = "station") val station: String,
    @ColumnInfo(name = "bookmark") val bookmark: Int = 0
)

fun UserLocationEntity.mapToExternalModel() =
    Location(
        `do` = enDo,
        sigungu = enSigungu,
        eupmyeondong = enEupmyeondong,
        station = station
    )