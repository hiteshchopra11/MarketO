package com.hiteshchopra.marketo.data.remote.source

import com.hiteshchopra.marketo.data.remote.model.EventDetailsResponse
import com.hiteshchopra.marketo.data.remote.retrofit.safeApiCall
import com.hiteshchopra.marketo.data.remote.service.EventsService
import com.hiteshchopra.marketo.domain.SafeResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class EventsDetailsRemoteSource(
    private val eventsService: EventsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IEventsRemoteSource {
    override suspend fun getEventDetails(): SafeResult<EventDetailsResponse> {
        return safeApiCall(dispatcher) {
            eventsService.getEventDetails()
        }
    }
}