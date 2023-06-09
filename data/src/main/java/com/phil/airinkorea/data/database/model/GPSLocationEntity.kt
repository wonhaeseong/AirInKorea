package com.phil.airinkorea.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.phil.airinkorea.data.model.Location

/**
 * 사용자의 GPS로 부터 얻어온 시군구 정보, 측정소 정보를 가지는 entity
 */
@Entity(
    tableName = "gps_location",
    primaryKeys = ["en_do", "en_sigungu", "en_eupmyeondong"]
)
data class GPSLocationEntity(
    @ColumnInfo(name = "row_id") val rowId: Int = 1,
    @ColumnInfo(name = "en_do") val enDo: String,
    @ColumnInfo(name = "en_sigungu") val enSigungu: String,
    @ColumnInfo(name = "en_eupmyeondong") val enEupmyeondong: String,
    @ColumnInfo(name = "station") val station: String
)

fun GPSLocationEntity.mapToExternalModel(): Location =
    Location(
        `do` = enDo,
        sigungu = enSigungu,
        eupmyeondong = enEupmyeondong,
        station = station
    )