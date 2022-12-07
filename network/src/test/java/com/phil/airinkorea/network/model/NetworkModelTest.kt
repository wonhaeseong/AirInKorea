package com.phil.airinkorea.network.model

import android.content.Context
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.net.HttpURLConnection


class NetworkModelTest {

    private lateinit var server: MockWebServer
    private lateinit var mockUrl: HttpUrl

    val successResponse by lazy {
        MockResponse().apply {
            setResponseCode(HttpURLConnection.HTTP_OK)
        }
    }


    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        mockUrl = server.url("/")
    }

    @Test
    fun `네트워크 직열화 테스트`() {

    }
}