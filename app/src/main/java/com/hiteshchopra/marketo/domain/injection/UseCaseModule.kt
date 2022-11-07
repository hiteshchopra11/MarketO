package com.hiteshchopra.marketo.domain.injection

import com.hiteshchopra.marketo.domain.repo.IEventsDetailsRepo
import com.hiteshchopra.marketo.domain.usecase.GetEventDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideEventDetailsUseCase(eventDetailsRepo: IEventsDetailsRepo): GetEventDetailsUseCase {
        return GetEventDetailsUseCase(eventDetailsRepo = eventDetailsRepo)
    }
}