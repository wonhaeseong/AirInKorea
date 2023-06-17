package com.phil.airinkorea.data.network.firebase

import android.util.Log
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.phil.airinkorea.data.network.model.NetworkAirData
import com.phil.airinkorea.data.network.model.NetworkLocation
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FirebaseClient {
    private val functions = FirebaseFunctions.getInstance("asia-northeast3")

    suspend fun getAirData(station: String): NetworkAirData? {
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
            return convertJsonToNetworkAirData(result.toString())
        } catch (e: Exception) {
            if (e is FirebaseFunctionsException) {
                val code = e.code
                val message = e.message
                Log.d("Firebase Exception", "code: $code")
                Log.d("Firebase Exception", "message: $message")
            }
            return null
        }
    }

    suspend fun getLocationByCoordinate(latitude: Double, longitude: Double): NetworkLocation? {
        val data = hashMapOf(
            "latitude" to latitude,
            "longitude" to longitude
        )
        try {
            val result =
                functions.getHttpsCallable("getLocationByCoordinate")
                    .call(data)
                    .await()
                    .data
            Log.d("API GPS result1", result.toString())
            return convertJsonToNetworkLocation(result.toString())
        } catch (e: Exception) {
            if (e is FirebaseFunctionsException) {
                val code = e.code
                val message = e.message
                Log.d("Firebase Exception", "code: $code")
                Log.d("Firebase Exception", "message: $message")
            }
            return null
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

    private fun convertJsonToNetworkLocation(result: String): NetworkLocation {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        return json.decodeFromString(result)
    }
}


