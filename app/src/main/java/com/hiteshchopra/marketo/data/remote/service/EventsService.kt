package com.hiteshchopra.marketo.data.remote.service

import com.hiteshchopra.marketo.data.Constants
import com.hiteshchopra.marketo.data.remote.model.EventDetailsResponse
import retrofit2.Retrofit
import retrofit2.http.GET

interface EventsService {
    companion object {
        private const val EVENTS = "events"
        fun createRetrofitService(retrofit: Retrofit): EventsService {
            return retrofit.create(EventsService::class.java)
        }
    }

    @GET(EVENTS)
    suspend fun getEventDetails(): EventDetailsResponse
}