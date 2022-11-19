package com.hiteshchopra.marketo.ui.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.hiteshchopra.marketo.ui.RequestPermission
import com.hiteshchopra.marketo.ui.SplashScreen
import com.hiteshchopra.marketo.ui.home.HomeScreen
import com.hiteshchopra.marketo.ui.home.HomeScreenVM

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    homeScreenVM: HomeScreenVM,
    closeApp: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }
        composable(
            route = Screen.Home.route
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                HomeScreen(
                    homeScreenVM = homeScreenVM,
                    closeApp = closeApp
                )
            }
        }

        composable(route = Screen.RequestPermission.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                RequestPermission(
                    homeScreenVM = homeScreenVM,
                    navController = navController
                )
            }
        }
    }
}