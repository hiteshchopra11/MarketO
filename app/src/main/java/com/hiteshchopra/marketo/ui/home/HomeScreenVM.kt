package com.hiteshchopra.marketo.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiteshchopra.marketo.R
import com.hiteshchopra.marketo.domain.SafeResult
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity
import com.hiteshchopra.marketo.domain.usecase.GetEventDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeScreenVM @Inject constructor(
    private val eventDetailsUseCase: GetEventDetailsUseCase
) : ViewModel() {

    /* StateFlow for publishing/observing the UI changes to Fragment */
    init {
        getEvents()
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: StateFlow<ViewState> = _viewState

    private fun getEvents() {
        viewModelScope.launch {
            val result = eventDetailsUseCase.perform()
            when (result) {
                is SafeResult.Failure -> {
                    _viewState.value = ViewState.Failure(result.exception)
                }
                SafeResult.NetworkError -> {
                    _viewState.value = ViewState.NetworkError
                }
                is SafeResult.Success -> {
                    val abc = fetchEventDetailsInfo(result.data as? EventDetailsEntity)
                    Log.d("TAG123", abc.toString())
                    _viewState.value =
                        ViewState.SuccessWithData(fetchEventDetailsInfo(result.data as? EventDetailsEntity))
                }
            }
        }
    }


    private fun fetchEventDetailsInfo(eventDetailsEntity: EventDetailsEntity?): List<EventDetailInfo?> {
        return eventDetailsEntity?.results?.map { eventDetails ->
            val coverImage: Int = fetchCoverImage(eventCategory = eventDetails?.category)
            val level = fetchLocalLevel(localLevel = eventDetails?.localRank)
            val duration = getEventDuration(duration = eventDetails?.duration?.toLong())
            val startDate = fetchDate(time = eventDetails?.start)
            val endDate = fetchDate(time = eventDetails?.end)
            EventDetailInfo(
                localRankLevel = level,
                id = eventDetails?.id.orEmpty(),
                coverPhoto = coverImage,
                title = eventDetails?.title.orEmpty(),
                description = eventDetails?.description.orEmpty(),
                category = eventDetails?.category.orEmpty(),
                label = eventDetails?.labels.orEmpty(),
                attendance = eventDetails?.phqAttendance ?: 0,
                duration = duration,
                startDate = startDate,
                endDate = endDate,
                timeZone = eventDetails?.timezone.orEmpty(),
                location = LatitudeLongitude(
                    latitude = eventDetails?.location?.first() ?: 0.0,
                    longitude = eventDetails?.location?.get(1) ?: 0.0
                ),
                country = eventDetails?.country.orEmpty()
            )
        } ?: arrayListOf()
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
}


sealed class ViewState {
    object Loading : ViewState()
    class SuccessWithData(val data: List<EventDetailInfo?>) : ViewState()
    class Failure(val exception: Exception? = null) : ViewState()
    object NetworkError : ViewState()
}