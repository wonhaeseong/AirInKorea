package com.phil.airinkorea.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.phil.airinkorea.database.dao.AddressDao
import com.phil.airinkorea.database.model.AddressEntity

@Database(
    entities = [AddressEntity::class],
    version = 1
)


abstract class AikDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao

    //TODO: 아래 companion object를 hilt 모듈로 이전해야함
    companion object {
        @Volatile
        private var INSTANCE: AikDatabase? = null
        fun getDatabase(context: Context): AikDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, AikDatabase::class.java, "addresses.db"
                )
                    .createFromAsset("database/address.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

