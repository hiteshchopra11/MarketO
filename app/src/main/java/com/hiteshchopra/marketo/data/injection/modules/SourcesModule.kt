package com.hiteshchopra.marketo.data.injection.modules

import com.hiteshchopra.marketo.data.remote.service.EventsService
import com.hiteshchopra.marketo.data.remote.source.EventsRemoteSource
import com.hiteshchopra.marketo.data.remote.source.IEventsRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SourcesModule {

    @Provides
    fun providesEventsRemoteSource(eventsService: EventsService): IEventsRemoteSource {
        return EventsRemoteSource(
            eventsService = eventsService
        )
    }
}