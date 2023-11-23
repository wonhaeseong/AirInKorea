package com.phil.airinkorea.data.locationservice

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.phil.airinkorea.data.UserGPSDataSource
import com.phil.airinkorea.data.model.Coordinate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val LOCATION_REQUEST_PRIORITY = Priority.PRIORITY_BALANCED_POWER_ACCURACY

class FusedLocationProvider @Inject constructor(
    @ApplicationContext context: Context
) : UserGPSDataSource {
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getUserCurrentCoordinate(): Coordinate? =
        withContext(Dispatchers.Default) {
            fusedLocationProviderClient.getCurrentLocation(
                LOCATION_REQUEST_PRIORITY,
                CancellationTokenSource().token
            ).await()?.let {
                Coordinate(
                    it.latitude,
                    it.longitude
                )
            }
        }
}