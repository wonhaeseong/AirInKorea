package com.phil.airinkorea.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.phil.airinkorea.data.model.Location
import com.phil.airinkorea.data.model.UserLocation

/**
 * 사용자가 추가한 위치 정보를 가지는 entity
 */
@Entity(
    tableName = "user_locations",
    primaryKeys = ["en_do", "en_sigungu", "en_eupmyeondong", "is_gps"]
)
data class UserLocationEntity(
    @ColumnInfo(name = "en_do") val enDo: String,
    @ColumnInfo(name = "en_sigungu") val enSigungu: String,
    @ColumnInfo(name = "en_eupmyeondong") val enEupmyeondong: String,
    @ColumnInfo(name = "station") val station: String,
    @ColumnInfo(name = "is_bookmark") val isBookmark: Int = 0,
    @ColumnInfo(name = "is_gps") val isGPS: Int = 0,
    @ColumnInfo(name = "is_selected") val isSelected: Int = 0,
)

fun UserLocationEntity.mapToExternalModel() =
    UserLocation(
        location = Location(
            `do` = enDo,
            sigungu = enSigungu,
            eupmyeondong = enEupmyeondong,
            station = station
        ),
        isGPS = isGPS == 1,
        isBookmark = isBookmark == 1,
        isSelected = isSelected == 1
    )