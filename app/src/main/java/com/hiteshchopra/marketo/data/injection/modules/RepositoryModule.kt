package com.hiteshchopra.marketo.data.injection.modules

import com.hiteshchopra.marketo.data.remote.model.EventDetailsMapper
import com.hiteshchopra.marketo.data.remote.repo.EventDetailsDetailsRepo
import com.hiteshchopra.marketo.data.remote.source.IEventsRemoteSource
import com.hiteshchopra.marketo.domain.repo.IEventsDetailsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesEventsRepo(
        eventsRemoteSource: IEventsRemoteSource, eventDetailsMapper: EventDetailsMapper
    ): IEventsDetailsRepo {
        return EventDetailsDetailsRepo(
            eventsRemoteSource = eventsRemoteSource, eventDetailsMapper = eventDetailsMapper
        )
    }
}