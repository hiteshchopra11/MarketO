package com.hiteshchopra.marketo.domain.usecase

import com.hiteshchopra.marketo.domain.SafeResult
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity
import com.hiteshchopra.marketo.domain.repo.IEventsDetailsRepo

class GetEventDetailsUseCase(
    private val eventDetailsRepo: IEventsDetailsRepo
) : BaseUseCase<SafeResult<EventDetailsEntity>, Unit> {

    override suspend fun perform(): SafeResult<EventDetailsEntity> {
        return eventDetailsRepo.getEvents()
    }
}