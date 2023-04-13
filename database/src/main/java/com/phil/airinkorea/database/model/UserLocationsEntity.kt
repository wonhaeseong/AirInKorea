package com.phil.airinkorea.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 사용자가 추가한 위치 정보를 가지는 entity
 */
@Entity(tableName = "user_locations")
data class UserLocationsEntity(
    @PrimaryKey
    @ColumnInfo(name = "row_id") val rowId: Int,
    @ColumnInfo(name = "en_do") val enDo: String,
    @ColumnInfo(name = "en_sigungu") val enSigungu: String,
    @ColumnInfo(name = "en_eupmyeondong") val enEupmyeondong: String,
    @ColumnInfo(name = "station") val station: String,
    @ColumnInfo(name = "bookmark") val bookmark: Int
)
