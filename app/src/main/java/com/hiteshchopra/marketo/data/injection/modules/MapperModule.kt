package com.hiteshchopra.marketo.data.injection.modules

import com.hiteshchopra.marketo.data.remote.model.EventDetailsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun providesEventDetailsMapper(): EventDetailsMapper {
        return EventDetailsMapper()
    }

}