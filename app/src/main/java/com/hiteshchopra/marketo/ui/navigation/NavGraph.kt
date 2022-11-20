package com.hiteshchopra.marketo.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.hiteshchopra.marketo.ui.RequestPermission
import com.hiteshchopra.marketo.ui.SplashScreen
import com.hiteshchopra.marketo.ui.home.EventDetailInfo
import com.hiteshchopra.marketo.ui.home.EventDetailInfoType
import com.hiteshchopra.marketo.ui.home.EventDetailsUI
import com.hiteshchopra.marketo.ui.home.HomeScreen
import com.hiteshchopra.marketo.ui.home.HomeScreenVM

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                    navController = navController,
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

        composable(
            route = Screen.EventDetails.route + "/{eventDetailsInfo}",
            arguments = listOf(navArgument("eventDetailsInfo") { type = EventDetailInfoType() })
        ) {
            val eventDetailInfo = it.arguments?.getParcelable<EventDetailInfo>("eventDetailsInfo")
            if (eventDetailInfo != null) {
                EventDetailsUI(eventDetails = eventDetailInfo)
            }
        }
    }
}