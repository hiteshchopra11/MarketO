package com.hiteshchopra.marketo

import android.app.Application
import androidx.compose.runtime.Composer
import androidx.compose.runtime.CompositionTracer
import androidx.compose.runtime.InternalComposeTracingApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarketApplication : Application() {
}