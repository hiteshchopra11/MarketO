package com.hiteshchopra.marketo.ui.home

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Parcelable
import android.util.Log
import androidx.annotation.StringDef
import java.util.*
import kotlinx.parcelize.Parcelize


@Suppress("DEPRECATION")
fun Geocoder.getAddress(
    latitude: Double, longitude: Double, address: (Address?) -> Unit
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, 1) {
            address(it.firstOrNull())
        }
        return
    }

    try {
        val a = getFromLocation(latitude, longitude, 1)?.firstOrNull()
        Log.d("TAG997", a.toString())
        address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
    } catch (e: Exception) {
        address(null)
    }
}


fun String.capitaliseFirstLetter(): String {
    return this.lowercase().replaceFirstChar {
        it.uppercase()
    }
}

enum class Level {
    Minor, Moderate, Important, Significant, Major
}

@Retention(AnnotationRetention.SOURCE)
@StringDef
annotation class EventCategory {
    companion object {
        const val CONFERENCES = "conferences"
        const val CONCERTS = "concerts"
        const val PERFORMING_ARTS = "performing-arts"
        const val SPORTS = "sports"
        const val COMMUNITY = "community"
        const val ALL = "all"
    }
}

@Parcelize
data class LatitudeLongitude(
    val latitude: Double,
    val longitude: Double
) : Parcelable

suspend fun getCityNameWithLocation(context: Context, lat: Double, long: Double): String {
    var location = "N/A"
    Geocoder(context, Locale("in")).getAddress(long, lat) { address: Address? ->
        if (address != null) {
            location = address.getAddressLine(0)
        }
    }
    return location
}