package com.phil.airinkorea

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.phil.airinkorea.manager.Coordinate
import com.phil.airinkorea.manager.LocationManager
import com.phil.airinkorea.manager.PermissionManager
import com.phil.airinkorea.manager.SettingManager
import com.phil.airinkorea.ui.NavGraph
import com.phil.airinkorea.viewmodel.AppInfoScreenActivityEvent
import com.phil.airinkorea.viewmodel.AppInfoViewModel
import com.phil.airinkorea.viewmodel.HomeScreenActivityEvent
import com.phil.airinkorea.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Resolver {
    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var settingManager: SettingManager
    @Inject
    lateinit var permissionManager: PermissionManager

    private val homeViewModel: HomeViewModel by viewModels()
    private val appInfoViewModel: AppInfoViewModel by viewModels()

    private val locationPermissionResultCaller =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    getGPSLocation()
                    Log.d("TAG a", "Coarse Location")
                }

                else -> {
                    homeViewModel.fetchToPageBookmark()
                    Toast.makeText(
                        this,
                        "Please turn on location permission to get location data",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("TAG a", "Permission 거부")
                }
            }
        }

    private val locationSettingResultCaller =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                getGPSLocation()
            } else {
                homeViewModel.fetchToPageBookmark()
                homeViewModel.setRefreshingState(false)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            val sysUicController = rememberSystemUiController()
            SideEffect {
                sysUicController.setStatusBarColor(
                    color = Color.Transparent
                )
            }
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }

        lifecycleScope.launch {
            homeViewModel.activityEvent.collect {
                when (it) {
                    HomeScreenActivityEvent.GetGPSLocation -> {
                        getGPSLocation()
                    }
                }
            }
        }
        lifecycleScope.launch {
            appInfoViewModel.activityEvent.collect {
                Log.d("TAG", it.toString())
                when (it) {
                    AppInfoScreenActivityEvent.ShowOpenSourceLicenses -> {
                        startOpenSourceLicensesActivity()
                    }

                    is AppInfoScreenActivityEvent.ShowGithubInBrowser -> {
                        openUriInBrowser(it.uri)
                    }
                }
            }
        }
    }

    private fun getGPSLocation() {
        lifecycleScope.launch {
            if (permissionManager.checkPermission()) {
                if (!settingManager.isLocationSettingOn()) {
                    settingManager.enableLocationSetting(this@MainActivity)
                } else {
                    val coordinate: Coordinate? =
                        locationManager.getLastKnownCoordinate()
                            ?: locationManager.getCurrentCoordinate()
                    if (coordinate == null) {
                        Toast.makeText(
                            this@MainActivity,
                            "Can't get Location Data",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        homeViewModel.fetchGPSLocation(
                            coordinate.latitude,
                            coordinate.longitude
                        )
                        Log.d("location", "널아님 $coordinate")
                    }
                }
            } else {
                permissionManager.requestCoarseLocationPermission(locationPermissionResultCaller)
            }
        }
    }

    private fun startOpenSourceLicensesActivity() {
        startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        OssLicensesMenuActivity.setActivityTitle(getString(R.string.open_source_licenses))
    }

    private fun openUriInBrowser(uri: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun startResolution(throwable: Throwable) {
        if (throwable is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest.Builder(
                    throwable.resolution
                ).build()
                locationSettingResultCaller.launch(intentSenderRequest)
            } catch (e: IntentSender.SendIntentException) {
                Log.d("GPS", e.message.toString())
            }
            Log.d("GPS1", "폰 위치 꺼져있음")
        }
    }
}