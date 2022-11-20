package com.hiteshchopra.marketo.ui.home

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventDetailInfo(
    val localRankLevel: Level,
    val id: String,
    @DrawableRes val coverPhoto: Int,
    val title: String,
    val description: String,
    val category: String,
    val label: List<String?>,
    val attendance: Int,
    val duration: String,
    val startDate: String,
    val endDate: String,
    val timeZone: String,
    val location: LatitudeLongitude,
    val country: String
) : Parcelable

class EventDetailInfoType : NavType<EventDetailInfo>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): EventDetailInfo? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): EventDetailInfo {
        return Gson().fromJson(value, EventDetailInfo::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: EventDetailInfo) {
        bundle.putParcelable(key, value)
    }
}