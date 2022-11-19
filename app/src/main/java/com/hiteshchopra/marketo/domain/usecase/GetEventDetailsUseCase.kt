package com.hiteshchopra.marketo.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hiteshchopra.marketo.data.remote.source.EventsPagingDataSource
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity
import com.hiteshchopra.marketo.domain.repo.IEventsDetailsRepo
import kotlinx.coroutines.flow.Flow

class GetEventDetailsUseCase(
    private val eventDetailsRepo: IEventsDetailsRepo
) {
    fun perform(params: Parameters): Flow<PagingData<EventDetailsEntity.Result>> {
        return Pager(
            pagingSourceFactory = {
                EventsPagingDataSource(
                    repo = eventDetailsRepo,
                    category = params.category,
                    countryCode = params.countryCode,
                    timeZone = params.timeZone,
                    date = params.date,
                    state = params.state
                )
            },
            config = PagingConfig(pageSize = 10)
        ).flow
    }

    data class Parameters(
        val category: String? = null,
        val countryCode: String,
        val timeZone: String,
        val date: String,
        val state: String
    )
}