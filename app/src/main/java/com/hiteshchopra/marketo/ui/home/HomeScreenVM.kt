package com.hiteshchopra.marketo.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hiteshchopra.marketo.R
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity
import com.hiteshchopra.marketo.domain.usecase.GetEventDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val eventDetailsUseCase: GetEventDetailsUseCase
) : ViewModel() {

    private var locationDetails: LocationDetails? = null

    fun setLocationDetails(location: LocationDetails) {
        locationDetails = location
    }

    fun getLocationDetails(): LocationDetails? {
        return locationDetails
    }

    fun categorySelected(eventCategory: String) {
        when (eventCategory) {
            EventCategory.CONFERENCES -> {
                R.drawable.conferences
            }
            EventCategory.CONCERTS -> {
                R.drawable.concert
            }
            EventCategory.PERFORMING_ARTS -> {
                R.drawable.performing_arts
            }
            EventCategory.SPORTS -> {
                R.drawable.sports
            }
            EventCategory.COMMUNITY -> {
                R.drawable.community
            }
            else -> {
                R.drawable.default_event
            }
        }
    }

    fun getEvents(
        state: String,
        category: String? = null,
        countryCode: String,
        debounce: Long = 0
    ): Flow<PagingData<EventDetailsEntity.Result>> {
        val timeZone = Calendar.getInstance().timeZone.getDisplayName(false, TimeZone.SHORT)
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


        return eventDetailsUseCase.perform(
            GetEventDetailsUseCase.Parameters(
                category = category,
                countryCode = locationDetails?.countryCode ?: countryCode,
                timeZone = timeZone,
                date = currentDate,
                state = state
            )
        ).debounce(debounce).cachedIn(viewModelScope)
    }

    fun fetchEventDetailsInfo(eventDetails: EventDetailsEntity.Result?): EventDetailInfo {
        val coverImage: Int = fetchCoverImage(eventCategory = eventDetails?.category)
        val level = fetchLocalLevel(localLevel = eventDetails?.localRank)
        val duration = getEventDuration(duration = eventDetails?.duration?.toLong())
        val startDate = fetchDate(time = eventDetails?.start)
        val endDate = fetchDate(time = eventDetails?.end)
        return EventDetailInfo(
            localRankLevel = level,
            id = eventDetails?.id.orEmpty(),
            coverPhoto = coverImage,
            title = eventDetails?.title.orNA(),
            description = eventDetails?.description.orNA(),
            category = eventDetails?.category.orNA(),
            label = eventDetails?.labels.orEmpty(),
            attendance = eventDetails?.phqAttendance ?: 0,
            duration = duration,
            startDate = startDate,
            endDate = endDate,
            timeZone = eventDetails?.timezone.orNA(),
            location = LatitudeLongitude(
                latitude = eventDetails?.location?.get(0) ?: 0.0,
                longitude = eventDetails?.location?.get(1) ?: 0.0
            ),
            country = eventDetails?.country.orNA()
        )
    }


    private fun fetchLocalLevel(localLevel: Int?): Level {
        return when (localLevel) {
            1 -> {
                Level.Minor
            }
            2 -> {
                Level.Moderate
            }
            3 -> {
                Level.Important
            }
            4 -> {
                Level.Significant
            }
            5 -> {
                Level.Major
            }
            else -> {
                Level.Important
            }
        }
    }

    private fun fetchCoverImage(eventCategory: String?): Int {
        return when (eventCategory) {
            EventCategory.CONFERENCES -> {
                R.drawable.conferences
            }
            EventCategory.CONCERTS -> {
                R.drawable.concert
            }
            EventCategory.PERFORMING_ARTS -> {
                R.drawable.performing_arts
            }
            EventCategory.SPORTS -> {
                R.drawable.sports
            }
            EventCategory.COMMUNITY -> {
                R.drawable.community
            }
            else -> {
                R.drawable.default_event
            }
        }
    }

    private fun getEventDuration(duration: Long?): String {
        if (duration == null || duration == 0L) {
            return "N/A"
        }
        val HH: Long = TimeUnit.MILLISECONDS.toHours(duration)
        val MM: Long = TimeUnit.MILLISECONDS.toMinutes(duration) % 60
        val SS: Long = TimeUnit.MILLISECONDS.toSeconds(duration) % 60

        return if (HH >= 1) {
            String.format("%02d:%02d:%02d", HH, MM, SS)
        } else {
            String.format("%02d:%02d", MM, SS)
        }
    }


    private fun fetchDate(time: String?): String {
        val dateTime: ZonedDateTime = ZonedDateTime.parse(time)
        val month = dateTime.month.name.capitaliseFirstLetter()
        val day = dateTime.dayOfMonth
        val year = dateTime.year
        return "$day ${month}, $year"
    }

    private fun fetchTodayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
}


sealed class ViewState {
    object Loading : ViewState()
    class SuccessWithData(val data: List<EventDetailInfo?>) : ViewState()
    class Failure(val exception: Exception? = null) : ViewState()
    object NetworkError : ViewState()
}

data class LocationDetails(
    val completeAddress: String,
    val city: String,
    val latitudeLongitude: LatitudeLongitude,
    val postalCode: String,
    val country: String,
    val countryCode: String
)