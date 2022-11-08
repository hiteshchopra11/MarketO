package com.hiteshchopra.marketo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hiteshchopra.marketo.R
import com.hiteshchopra.marketo.ui.navigation.Screen

@Composable
fun SplashScreen(modifier: Modifier, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101e22))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101e22))
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_config))
            val logoAnimationState = animateLottieCompositionAsState(composition = composition)
            LottieAnimation(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth(),
                composition = composition,
                progress = { logoAnimationState.progress },
                contentScale = ContentScale.FillWidth
            )
            if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
                navController.navigate(Screen.Home.route) {
                    popUpTo("splash_screen") {
                        inclusive = true
                    }
                }
            }
        }
    }
}