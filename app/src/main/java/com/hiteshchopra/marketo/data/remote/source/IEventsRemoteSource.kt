package com.hiteshchopra.marketo.data.remote.source

import com.hiteshchopra.marketo.data.remote.model.EventDetailsResponse
import com.hiteshchopra.marketo.domain.SafeResult

interface IEventsRemoteSource {
    suspend fun getEventDetails(
        category: String? = null,
        countryCode: String,
        timeZone: String,
        date: String,
        state: String,
        limit: Int,
        offset: Int
    ): SafeResult<EventDetailsResponse>
}