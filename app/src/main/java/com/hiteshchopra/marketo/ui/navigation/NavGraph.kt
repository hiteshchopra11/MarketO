package com.hiteshchopra.marketo.ui.navigation


import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
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
fun SetupNavGraph(navController: NavHostController, homeScreenVM: HomeScreenVM) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }
        composable(route = Screen.Home.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
//                RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                HomeScreen(homeScreenVM = homeScreenVM)
            }
        }
    }
}