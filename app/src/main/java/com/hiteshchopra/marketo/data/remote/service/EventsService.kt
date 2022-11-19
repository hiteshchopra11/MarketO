package com.hiteshchopra.marketo.data.remote.service

import com.hiteshchopra.marketo.data.remote.model.EventDetailsResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsService {
    companion object {
        private const val EVENTS = "events"
        fun createRetrofitService(retrofit: Retrofit): EventsService {
            return retrofit.create(EventsService::class.java)
        }
    }

    @GET(EVENTS)
    suspend fun getAllEventDetails(
        @Query("country") country: String,
        @Query("timezone") timezone: String,
        @Query("state") state: String,
        @Query("scope") scope: String,
        @Query("relevance") relevance: String,
        @Query("start_around.origin") startDate: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): EventDetailsResponse

    @GET(EVENTS)
    suspend fun getEventDetailsByCategory(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("timezone") timezone: String,
        @Query("state") state: String,
        @Query("scope") scope: String,
        @Query("relevance") relevance: String,
        @Query("start_around.origin") startDate: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): EventDetailsResponse
}