package com.hiteshchopra.marketo.domain.repo


import com.hiteshchopra.marketo.domain.SafeResult
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity

interface IEventsDetailsRepo {
    suspend fun getEvents(): SafeResult<EventDetailsEntity>
}