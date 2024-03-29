package com.phil.airinkorea.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phil.airinkorea.ui.addlocation.AddLocationRoute
import com.phil.airinkorea.ui.appguide.AppGuideRoute
import com.phil.airinkorea.ui.appinfo.AppInfoRoute
import com.phil.airinkorea.ui.home.HomeRoute
import com.phil.airinkorea.ui.managelocations.ManageLocationRoute


@Composable
fun NavGraph(
    navController: NavHostController
) {
    val systemUiController = rememberSystemUiController()
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = false
                )
            }
            HomeRoute(
                onManageLocationClick = { navController.navigate("ManageLocation") },
                onParticulateMatterInfoClick = { navController.navigate("AppGuide") },
                onAppInfoClick = { navController.navigate("AppInfo") }
            )
        }
        composable("ManageLocation") {
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }
            ManageLocationRoute(
                onBackButtonClick = { navController.popBackStack() },
                onAddLocationButtonClick = { navController.navigate("AddLocation") }
            )
        }
        composable("AddLocation") {
            AddLocationRoute(
                onBackButtonClick = { navController.popBackStack() }
            )
        }
        composable("AppInfo") {
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }
            AppInfoRoute(
                onBackButtonClick = { navController.popBackStack() }
            )
        }
        composable("AppGuide") {
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }
            AppGuideRoute(
                onBackButtonClick = { navController.popBackStack() }
            )
        }
    }
}