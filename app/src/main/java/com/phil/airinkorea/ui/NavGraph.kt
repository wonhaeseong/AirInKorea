package com.phil.airinkorea.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.phil.airinkorea.ui.addlocation.AddLocationRoute
import com.phil.airinkorea.ui.home.HomeRoute
import com.phil.airinkorea.ui.managelocations.ManageLocationRoute

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeRoute(
                onManageLocationClick = { navController.navigate("ManageLocation") },
                onParticulateMatterInfoClick = { navController.navigate("ParticulateMatterInfo") },
                onAppInfoClick = { navController.navigate("AppInfo") }
            )
        }
        composable("ManageLocation") {
            ManageLocationRoute(
                onBackButtonClick = { navController.popBackStack() },
                onAddLocationButtonClick = {navController.navigate("AddLocation")}
            )
        }
        composable("AddLocation") {
            AddLocationRoute(
                onBackButtonClick = { navController.popBackStack() }
            )
        }
        composable("AppInfo") {

        }
        composable("ParticulateMatterInfo") {

        }
    }
}