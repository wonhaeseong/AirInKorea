package com.phil.airinkorea.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey
    @ColumnInfo(name = "rowid") val rowId: Int,
    @ColumnInfo(name = "en_address") val enAddress: String,
    @ColumnInfo(name = "tm_x") val tmX: Double,
    @ColumnInfo(name = "tm_y") val tmY: Double
)