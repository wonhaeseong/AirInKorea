package com.phil.airinkorea

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.phil.airinkorea.domain.model.AirLevel
import com.phil.airinkorea.ui.NavGraph
import com.phil.airinkorea.ui.theme.AIKTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

/**
 * location 먼저 요청 후, gps 요청
 * case 1 -> GPS x, location x -> 값을 가져올 수 없음 AirLevel.ERROR, 배너 -> GPS, location 허용
 * case 2 -> GPS o, location x -> 값을 가져올 수 없음 AirLevel.ERROR, 배너 -> location 허용
 * case 3 -> GPS x, location o -> 값을 가져올 수 없음 AirLevel.ERROR, 배너 -> GPS 허용
 * case 4 -> GPS o, location o -> Loading
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    val isGPSScreen: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                Log.d("GPS", "GPSPermmistionLauncher실향ㄷ홤")
                if (isGranted) {
                    Log.d("GPS", "GPSPermmistionLauncher1")
                    updateLocation()
                } else {
                    Log.d("GPS", "Canceled")
                }
            }
        setContent {
            val navController = rememberNavController()
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent
                )
            }
            NavGraph(navController = navController)
        }
        checkLocationPermissionPhone()
        updateLocation()
    }

    private val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 6000L).apply {
            setMinUpdateDistanceMeters(100f)
            setWaitForAccurateLocation(true)
        }.build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation?.let { location ->
                val geocoder = Geocoder(this@MainActivity, Locale.US)
                Log.d("GPS latitude", location.latitude.toString())
                Log.d("GPS longitude", location.longitude.toString())

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(
                        location.latitude, location.longitude, 1
                    ) { Log.d("GPS address", it[0].getAddressLine(0)) }
                } else {
                    geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        ?.get(0)
                        ?.getAddressLine(0)?.let {
                            Log.d("GPS address", it)
                        }
                }
            }
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    //앱의 위치정보 허용여부 확인
    private fun checkLocationPermission(): Boolean {
        val permissionResult = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        var result = false
        when (permissionResult) {
            PackageManager.PERMISSION_GRANTED -> {
                Log.d("GPS", "Granted")
                result = true
            }

            PackageManager.PERMISSION_DENIED -> {
                Log.d("GPS", "denied")
            }
        }
        return result
    }

    private fun updateLocation() {
        if (checkLocationPermission()) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            Log.d("GPS", "Updated")
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    //휴대폰 위치설정 확인
    private fun checkLocationPermissionPhone() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val result: Task<LocationSettingsResponse> =
            client.checkLocationSettings(builder.build())
        result.addOnSuccessListener {
            Log.d("GPS", "폰 위치 켜짐")
        }.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                Log.d("GPS", "폰 위치 꺼짐")
                try {
                    exception.startResolutionForResult(
                        this@MainActivity,
                        100
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.d("GPS", e.message.toString())
                }
            }
        }
    }
}