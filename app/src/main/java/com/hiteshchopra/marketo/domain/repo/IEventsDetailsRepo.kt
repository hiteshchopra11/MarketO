package com.hiteshchopra.marketo.domain.repo


import com.hiteshchopra.marketo.domain.SafeResult
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity

interface IEventsDetailsRepo {
    suspend fun getEvents(
        category: String? = null,
        countryCode: String,
        timeZone: String,
        date: String,
        state: String,
        offset: Int,
        limit: Int
    ): SafeResult<EventDetailsEntity>
}