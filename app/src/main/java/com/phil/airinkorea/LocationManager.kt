package com.phil.airinkorea

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class LocationManager @Inject constructor(@ApplicationContext context: Context) {
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    suspend fun getLastKnownCoordinate(): Coordinate? {
        return withContext(Dispatchers.IO) {
            val lastLocation = fusedLocationProviderClient.lastLocation.await()
            if (lastLocation == null) {
                null
            } else {
                Coordinate(latitude = lastLocation.latitude, longitude = lastLocation.longitude)
            }
        }
    }

    suspend fun getCurrentCoordinate(): Coordinate? {
        return withContext(Dispatchers.IO) {
            val currentLocation = fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token
            ).await()
            if (currentLocation == null) {
                null
            } else {
                Coordinate(
                    latitude = currentLocation.latitude,
                    longitude = currentLocation.longitude
                )
            }
        }
    }
}

data class Coordinate(val latitude: Double, val longitude: Double)