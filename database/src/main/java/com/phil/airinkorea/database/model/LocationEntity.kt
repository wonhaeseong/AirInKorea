package com.phil.airinkorea.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.phil.airinkorea.domain.model.Location

/**
 * prepopulated db인 locations.db의 entity
 * 영문 시군구 별 station 정보를 가지고 있음
 */
@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey
    @ColumnInfo(name = "rowid") val rowId: Int,
    @ColumnInfo(name = "en_do") val enDo: String,
    @ColumnInfo(name = "en_sigungu") val enSigungu: String,
    @ColumnInfo(name = "en_eupmyeondong") val enEupmyeondong: String,
    @ColumnInfo(name = "station") val station: String
)

fun LocationEntity.mapToExternalModel(): Location =
    Location(
        `do`=enDo,
        sigungu = enSigungu,
        eupmyeondong = enEupmyeondong,
        station = station
    )
