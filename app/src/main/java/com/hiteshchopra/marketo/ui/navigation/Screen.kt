package com.hiteshchopra.marketo.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object RequestPermission : Screen("request_permission")
}