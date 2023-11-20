package com.phil.airinkorea.manager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationManager @Inject constructor(@ApplicationContext context: Context) {
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    //    @SuppressLint("MissingPermission")
//    suspend fun getLastKnownCoordinate(): Coordinate? {
//        return withContext(Dispatchers.IO) {
//            val startTime = System.currentTimeMillis()
//            val lastLocation = fusedLocationProviderClient.lastLocation.await()
//            val endTime = System.currentTimeMillis()
//            val executionTime = endTime - startTime
//            Log.d("last known location", "Execution time: $executionTime milliseconds")
//            if (lastLocation == null) {
//                null
//            } else {
//                Coordinate(latitude = lastLocation.latitude, longitude = lastLocation.longitude)
//            }
//        }
//    }
    @SuppressLint("MissingPermission")
    suspend fun getCurrentCoordinate(): Coordinate? = withContext(Dispatchers.IO) {
        val result = fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token
        ).addOnFailureListener {
            Log.d("location", "Failure \n cause:${it.cause} \n message:${it.message}")
        }.await()
        result?.let {
            Coordinate(
                result.latitude,
                result.longitude
            )
        }
    }
}

data class Coordinate(val latitude: Double, val longitude: Double)