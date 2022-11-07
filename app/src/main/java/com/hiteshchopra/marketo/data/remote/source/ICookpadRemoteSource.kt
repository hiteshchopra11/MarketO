package com.hiteshchopra.marketo.data.remote.source

import com.hiteshchopra.marketo.data.remote.model.EventDetailsResponse
import com.hiteshchopra.marketo.domain.SafeResult

interface IEventsRemoteSource {
    suspend fun getEventDetails(): SafeResult<EventDetailsResponse>
}