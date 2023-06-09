package com.phil.airinkorea.data.network.firebase

import android.util.Log
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.phil.airinkorea.data.network.model.NetworkAirData
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

            Log.d("API result1",result.toString())
            Log.d("API result2",convertJsonToNetworkAirData(result.toString()).toString())
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

    private fun convertJsonToNetworkAirData(result: String): NetworkAirData {
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        return json.decodeFromString(result)
    }
}


