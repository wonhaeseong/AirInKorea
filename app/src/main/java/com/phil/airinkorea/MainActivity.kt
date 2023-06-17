package com.phil.airinkorea

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.phil.airinkorea.ui.NavGraph
import com.phil.airinkorea.ui.viewmodel.ActivityEvent
import com.phil.airinkorea.ui.viewmodel.HomeUiState
import com.phil.airinkorea.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationPermissionResultCaller =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    getGPSLocation()
                    Log.d("TAG a", "Fine Location")
                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    getGPSLocation()
                    Log.d("TAG a", "Coarse Location")
                }

                else -> {
                    Log.d("TAG a", "Permission 거부")
                }
            }
        }

    private val GPSRequestResultCaller =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                getGPSLocation()
                Log.d("TAG a", "GPS 허용됨")
            } else {
                Log.d("TAG a", "GPS 허용안됨")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController, homeViewModel = homeViewModel)
        }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        lifecycleScope.launch(Dispatchers.IO) {
            homeViewModel.activityEvent.collect {
                when (it) {
                    ActivityEvent.GetGPSLocation -> {
                        getGPSLocation()
                    }
                }
            }
        }
    }

    private fun requestPermission() {
        locationPermissionResultCaller.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun checkPermission(): Boolean {
        return (
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )) && (
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
    }

    private fun getGPSLocation() {
        if (checkPermission()) {
            val builder = LocationSettingsRequest.Builder()
            val client: SettingsClient = LocationServices.getSettingsClient(this)
            val task: Task<LocationSettingsResponse> =
                client.checkLocationSettings(builder.build())
            task.addOnSuccessListener {
                Log.d("GPS1", "폰 위치 켜져있음")
                fusedLocationProviderClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).addOnSuccessListener { location ->
                    Log.d("GPS latitude", location.latitude.toString())
                    Log.d("GPS longitude", location.longitude.toString())
                    homeViewModel.fetchGPSLocation(location.latitude, location.longitude)
                }.addOnFailureListener {
                    Toast.makeText(this, "Can't get Location Data", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        val intentSenderRequest = IntentSenderRequest.Builder(
                            exception.resolution
                        ).build()
                        GPSRequestResultCaller.launch(intentSenderRequest)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.d("GPS", e.message.toString())
                    }
                    Log.d("GPS1", "폰 위치 꺼져있음")
                }
            }
        } else {
            requestPermission()
        }
    }
}