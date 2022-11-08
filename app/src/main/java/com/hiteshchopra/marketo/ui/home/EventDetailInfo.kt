package com.hiteshchopra.marketo.ui.home

import androidx.annotation.DrawableRes

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
)