package com.hiteshchopra.marketo.data.remote.source

import com.hiteshchopra.marketo.data.remote.model.EventDetailsResponse
import com.hiteshchopra.marketo.data.remote.retrofit.safeApiCall
import com.hiteshchopra.marketo.data.remote.service.EventsService
import com.hiteshchopra.marketo.domain.SafeResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class EventsRemoteSource(
    private val eventsService: EventsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IEventsRemoteSource {
    companion object {
        private const val REGION = "region"
        private const val LOCATION_AROUND = "location_around"
    }

    override suspend fun getEventDetails(
        category: String?,
        countryCode: String,
        timeZone: String,
        date: String,
        state: String,
        limit: Int,
        offset: Int
    ): SafeResult<EventDetailsResponse> {
        return safeApiCall(dispatcher) {
            if (category == null) {
                eventsService.getAllEventDetails(
                    country = countryCode,
                    timezone = timeZone,
                    state = state,
                    scope = REGION,
                    relevance = LOCATION_AROUND,
                    startDate = date,
                    limit = limit,
                    offset = offset
                )
            } else {
                eventsService.getEventDetailsByCategory(
                    category = category,
                    country = countryCode,
                    timezone = timeZone,
                    state = state,
                    scope = REGION,
                    relevance = LOCATION_AROUND,
                    startDate = date,
                    limit = limit,
                    offset = offset
                )
            }
        }
    }
}