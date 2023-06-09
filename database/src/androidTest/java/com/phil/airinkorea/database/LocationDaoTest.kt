package com.phil.airinkorea.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.phil.airinkorea.data.database.AIKDatabase
import com.phil.airinkorea.data.database.dao.LocationDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LocationDaoTest {

    private lateinit var locationDao: LocationDao
    private lateinit var db: AIKDatabase

    /**
     *  AddressDao는 db에서 수정, 생성, 삭제하지 않고 읽기만 하기 때문에
     *  inMemoryDatabase로 생성하지 않았다.
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(context, AIKDatabase::class.java, "addresses.db")
            .createFromAsset("database/address.db")
            .build()
        locationDao = db.locationDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `주소가져오기테스트`() = runTest {

    }
}