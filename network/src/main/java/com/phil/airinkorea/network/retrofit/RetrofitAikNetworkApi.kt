package com.phil.airinkorea.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.phil.airinkorea.network.BuildConfig
import com.phil.airinkorea.network.model.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private interface RetrofitAikNetworkApi {

    /**
     * 읍면동이름으로  지역의 좌표가져옴([NetworkLocation])
     * @param serviceKey 서버에서 할당받은 키값
     * @param umdName 읍면동이름 ex) 용현동
     */
    @GET("/B552584/MsrstnInfoInqireSvc/getTMStdrCrdnt")
    suspend fun getStationLocation(
        @Query("serviceKey") serviceKey: String?,
        @Query("returnType") returnType: String?,
        @Query("numOfRows") numOfRows: Int?,
        @Query("umdName") umdName: String?
    ): NetworkResponse<NetworkLocation>

    /**
     *  [getStationLocation]으로 가져온 [NetworkLocation]정보를 통해
     *  해당 지역의 좌표를 얻고 좌표를 매개변수로 가까운 측정소들을 가져옴
     *  @param serviceKey 서버에서 할당받은 키값
     *  @param tmX tmX 좌표
     *  @param tmY tmY 좌표
     */
    @GET("/B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList")
    suspend fun getNearbyStation(
        @Query("serviceKey") serviceKey: String?,
        @Query("returnType") returnType: String?,
        @Query("tmX") tmX: String?,
        @Query("tmY") tmY: String?
    ): NetworkResponse<NetworkNearbyStation>

    /**
     * [getNearbyStation]으로 가져온 [NetworkNearbyStation]정보를 통해
     * 측정소 이름을 가져오고 이름을 매개변수로 해당 측정소의 미세먼지 정보를
     * 가져옴
     * @param serviceKey 서버에서 할당받은 키값
     * @param stationName 측정소 이름
     */
    @GET("/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
    suspend fun getAirData(
        @Query("serviceKey") serviceKey: String?,
        @Query("returnType") returnType: String?,
        @Query("numOfRows") numOfRows: Int?,
        @Query("stationName") stationName: String?,
        @Query("dataTerm") dataTerm: String?
    ): NetworkResponse<NetworkAirData>
}

private const val AikBaseUrl = BuildConfig.BASE_URL

class RetrofitAikNetwork : AikNetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(AikBaseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                ).build()
        )
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            Json.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitAikNetworkApi::class.java)

    override suspend fun getStationLocation(
        serviceKey: String?,
        returnType: String,
        numOfRows: Int,
        umdName: String?
    ): NetworkResultBody<NetworkLocation> =
        networkApi.getStationLocation(
            serviceKey = serviceKey,
            returnType = returnType,
            numOfRows = numOfRows,
            umdName = umdName,
        ).response.body

    override suspend fun getNearbyStation(
        serviceKey: String?,
        returnType: String,
        tmX: String?,
        tmY: String?
    ): NetworkResultBody<NetworkNearbyStation> =
        networkApi.getNearbyStation(
            serviceKey = serviceKey,
            returnType = returnType,
            tmX = tmX,
            tmY = tmY
        ).response.body

    override suspend fun getAirData(
        serviceKey: String?,
        returnType: String,
        numOfRows: Int,
        stationName: String?,
        dataTerm: String
    ): NetworkResultBody<NetworkAirData> =
        networkApi.getAirData(
            serviceKey = serviceKey,
            returnType = returnType,
            numOfRows = numOfRows,
            stationName = stationName,
            dataTerm = dataTerm
        ).response.body
}