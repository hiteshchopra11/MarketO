package com.hiteshchopra.marketo.data.remote.repo

import com.hiteshchopra.marketo.data.remote.model.EventDetailsMapper
import com.hiteshchopra.marketo.data.remote.source.IEventsRemoteSource
import com.hiteshchopra.marketo.domain.SafeResult
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity
import com.hiteshchopra.marketo.domain.repo.IEventsDetailsRepo

class EventDetailsDetailsRepo(
    private val eventsRemoteSource: IEventsRemoteSource,
    private val eventDetailsMapper: EventDetailsMapper
) : IEventsDetailsRepo {
    override suspend fun getEvents(): SafeResult<EventDetailsEntity> {
        return when (val response = eventsRemoteSource.getEventDetails()) {
            is SafeResult.Success -> SafeResult.Success(eventDetailsMapper.mapToDomain(response.data))
            is SafeResult.Failure -> SafeResult.Failure(response.exception)
            is SafeResult.NetworkError -> SafeResult.NetworkError
        }
    }
}