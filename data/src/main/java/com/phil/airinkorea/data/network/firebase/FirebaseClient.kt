package com.phil.airinkorea.data.network.firebase

import android.util.Log
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.phil.airinkorea.data.NetworkDataSource
import com.phil.airinkorea.data.network.model.NetworkAirData
import com.phil.airinkorea.data.network.model.NetworkCoordinateResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FirebaseClient @Inject constructor(): NetworkDataSource {
    private val functions = FirebaseFunctions.getInstance("asia-northeast3")

    override suspend fun getAirData(station: String): NetworkAirData? = withContext(Dispatchers.IO) {
        val data = hashMapOf(
            "stationName" to station
        )

        try {
            val result =
                functions.getHttpsCallable("getAirData")
                    .call(data)
                    .await()
                    .data

            Log.d("API result1", result.toString())
            Log.d("API result2", convertJsonToNetworkAirData(result.toString()).toString())
            convertJsonToNetworkAirData(result.toString())
        } catch (e: Exception) {
            if (e is FirebaseFunctionsException) {
                val code = e.code
                val message = e.message
                Log.d("Firebase Exception", "code: $code")
                Log.d("Firebase Exception", "message: $message")
            }
            null
        }
    }

    override suspend fun getAirDataByCoordinate(
        latitude: Double,
        longitude: Double
    ): NetworkCoordinateResult? = withContext(Dispatchers.IO){
        val data = hashMapOf(
            "latitude" to latitude,
            "longitude" to longitude
        )
        try {
            val result =
                functions.getHttpsCallable("getAirDataByCoordinate")
                    .call(data)
                    .await()
                    .data
            Log.d("API GPS result1", result.toString())
            convertJsonToNetworkCoordinate(result.toString())
        } catch (e: Exception) {
            if (e is FirebaseFunctionsException) {
                val code = e.code
                val message = e.message
                Log.d("Firebase Exception", "code: $code")
                Log.d("Firebase Exception", "message: $message")
            }
            null
        }
    }

    private fun convertJsonToNetworkAirData(result: String): NetworkAirData {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        return json.decodeFromString(result)
    }

    private fun convertJsonToNetworkCoordinate(result: String): NetworkCoordinateResult {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        return json.decodeFromString(result)
    }
}


