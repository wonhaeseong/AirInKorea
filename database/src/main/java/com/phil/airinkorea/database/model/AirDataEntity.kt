package com.phil.airinkorea.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "airData")
data class AirDataEntity(
    @ColumnInfo(name = "pm10_value") val pm10Value: String?,
    @ColumnInfo(name = "pm25_value") val pm25Value: String?,
    @ColumnInfo(name = "so2_value") val so2Value: String?,
    @ColumnInfo(name = "co_value") val coValue: String?,
    @ColumnInfo(name = "o3_value") val o3Value: String?,
    @ColumnInfo(name = "khai_value") val khaiValue: String?,
    @ColumnInfo(name = "no2_value") val no2Value: String?,
    @ColumnInfo(name = "pm10_flag") val pm10Flag: String?,
    @ColumnInfo(name = "pm25_flag") val pm25Flag: String?,
    @ColumnInfo(name = "co_flag") val coFlag: String?,
    @ColumnInfo(name = "no2_flag") val no2Flag: String?,
    @ColumnInfo(name = "o3_flag") val o3Flag: String?,
    @ColumnInfo(name = "so2_flag") val so2Flag: String?,
    @ColumnInfo(name = "so2_grade") val so2Grade: String?,
    @ColumnInfo(name = "o3_grade") val o3Grade: String?,
    @ColumnInfo(name = "co_grade") val coGrade: String?,
    @ColumnInfo(name = "pm10_grade") val pm10Grade: String?,
    @ColumnInfo(name = "khai_grade") val khaiGrade: String?,
    @ColumnInfo(name = "no2_grade") val no2Grade: String?,
    @ColumnInfo(name = "dataTime") val dataTime: String?,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid") val rowId: Int = 0
}

