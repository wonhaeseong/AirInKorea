package com.phil.airinkorea.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.phil.airinkorea.database.dao.AddressDao
import com.phil.airinkorea.database.model.AddressEntity
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
class AddressDaoTest {

    private lateinit var addressDao: AddressDao
    private lateinit var db: AikDatabase

    /**
     *  AddressDao는 db에서 수정, 생성, 삭제하지 않고 읽기만 하기 때문에
     *  inMemoryDatabase로 생성하지 않았다.
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(context, AikDatabase::class.java, "addresses.db")
            .createFromAsset("database/address.db")
            .build()
        addressDao = db.addressDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `주소가져오기테스트`() = runTest {

        val hamyangAddresses: List<AddressEntity> = listOf(
            AddressEntity(rowId=1512, enAddress="Macheon-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=261692.5315, tmY=208527.191),
            AddressEntity(rowId=1513, enAddress="Baekjeon-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=257396.8495, tmY=229938.8355),
            AddressEntity(rowId=1514, enAddress="Seosang-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=261470.3133, tmY=243924.3079),
            AddressEntity(rowId=1515, enAddress="Seoha-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=264970.4192, tmY=237116.2367),
            AddressEntity(rowId=1516, enAddress="Sudong-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=273508.0126, tmY=228963.948),
            AddressEntity(rowId=1517, enAddress="Anui-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=272761.7452, tmY=239859.385),
            AddressEntity(rowId=1518, enAddress="Yurim-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=270991.7904, tmY=220966.4293),
            AddressEntity(rowId=1519, enAddress="Jigok-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=268900.246, tmY=231168.9175),
            AddressEntity(rowId=1520, enAddress="Hamyang-eup, Hamyang-gun, Gyeongsangnam-do", tmX=263735.1474, tmY=222439.8811),
            AddressEntity(rowId=1521, enAddress="Hyucheon-myeon, Hamyang-gun, Gyeongsangnam-do", tmX=266681.6721, tmY=216426.553)
        )

        val addressEntities = addressDao.getAddresses("hamyang")
        assertEquals(
            hamyangAddresses, addressEntities
        )

    }
}