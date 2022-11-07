package com.hiteshchopra.marketo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.hiteshchopra.marketo.domain.usecase.GetEventDetailsUseCase
import com.hiteshchopra.marketo.ui.navigation.SetupNavGraph
import com.hiteshchopra.marketo.ui.home.HomeScreenVM
import com.hiteshchopra.marketo.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeScreenVM: HomeScreenVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, homeScreenVM = homeScreenVM)
//                RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)

            }
        }
    }
}