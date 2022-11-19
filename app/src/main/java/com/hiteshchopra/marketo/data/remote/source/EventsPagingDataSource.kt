package com.hiteshchopra.marketo.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hiteshchopra.marketo.domain.getSuccessOrNull
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity
import com.hiteshchopra.marketo.domain.repo.IEventsDetailsRepo

class EventsPagingDataSource(
    private val repo: IEventsDetailsRepo,
    private val category: String? = null,
    private val countryCode: String,
    private val timeZone: String,
    private val date: String,
    private val state: String,
) : PagingSource<Int, EventDetailsEntity.Result>() {

    companion object {
        const val LIMIT = 10
    }

    override fun getRefreshKey(state: PagingState<Int, EventDetailsEntity.Result>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventDetailsEntity.Result> {
        return try {
            val page = params.key ?: 1
            val offset = (page - 1) * LIMIT

            val response = repo.getEvents(
                category = category,
                countryCode = countryCode,
                timeZone = timeZone,
                date = date,
                state = state,
                offset = offset,
                limit = LIMIT
            ).getSuccessOrNull()

            LoadResult.Page(
                data = response?.results?.filterNotNull() ?: listOf(),
                prevKey = null,
                nextKey = if (response?.results?.isEmpty() == true) null else page + 1,
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
