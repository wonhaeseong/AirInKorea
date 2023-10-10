package com.phil.airinkorea

import android.content.Context
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

//private val LOCATION_SETTINGS_REQUEST_CODE = SettingManager::class.java.hashCode() and 0xffff
@Singleton
class SettingManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    /**
     * 이미 Location 설정이 켜져 있다면 아무 일도 하지 않음
     */
    suspend fun enableLocationSetting(resolver: Resolver) {
        return withContext(Dispatchers.IO) {
            val locationRequest =
                LocationRequest.Builder(Priority.PRIORITY_LOW_POWER, 6000L).apply {
                    setMinUpdateDistanceMeters(100f)
                    setWaitForAccurateLocation(true)
                }.build()
            val locationSettingRequest =
                LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()
            val settingClient = LocationServices.getSettingsClient(context)
            try {
                settingClient.checkLocationSettings(locationSettingRequest).await()
            } catch (e: Throwable) {
                onCheckSettingsFailure(e, resolver)
            }
        }
    }

    suspend fun isLocationSettingOn(): Boolean {
        return withContext(Dispatchers.IO) {
            val locationSettingRequest =
                LocationSettingsRequest.Builder().build()
            val settingClient = LocationServices.getSettingsClient(context)
            val settingState = settingClient.checkLocationSettings(locationSettingRequest)
                .await().locationSettingsStates
            settingState?.isGpsUsable ?: false
        }
    }

    private fun onCheckSettingsFailure(throwable: Throwable, resolver: Resolver) =
        if (throwable is ResolvableApiException) {
            resolver.startResolution(throwable)
        } else {
            throw throwable
        }
}
