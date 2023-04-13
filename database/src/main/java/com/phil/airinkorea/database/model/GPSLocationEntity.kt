package com.phil.airinkorea.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 사용자의 GPS로 부터 얻어온 시군구 정보, 측정소 정보를 가지는 entity
 */
@Entity(tableName = "gps_location")
data class GPSLocationEntity(
    @PrimaryKey
    @ColumnInfo(name = "row_id") val rowId: Int,
    @ColumnInfo(name = "en_do") val enDo: String,
    @ColumnInfo(name = "en_sigungu") val enSigungu: String,
    @ColumnInfo(name = "en_eupmyeondong") val enEupmyeondong: String,
    @ColumnInfo(name = "station") val station: String
)