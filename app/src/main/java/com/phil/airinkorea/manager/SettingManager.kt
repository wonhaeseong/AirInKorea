package com.phil.airinkorea.manager

import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private const val LOCATION_MINIMUM_UPDATE_DISTANCE = 100f
private const val LOCATION_REQUEST_INTERVAL = 6000L
private const val LOCATION_REQUEST_PRIORITY = Priority.PRIORITY_LOW_POWER

@Singleton
class SettingManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun enableLocationSetting() {
        val locationRequest =
            LocationRequest.Builder(LOCATION_REQUEST_PRIORITY, LOCATION_REQUEST_INTERVAL)
                .apply {
                    setMinUpdateDistanceMeters(LOCATION_MINIMUM_UPDATE_DISTANCE)
                    setWaitForAccurateLocation(true)
                }.build()
        val locationSettingRequest =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()
        LocationServices.getSettingsClient(context)
            .checkLocationSettings(locationSettingRequest).await()
    }
}