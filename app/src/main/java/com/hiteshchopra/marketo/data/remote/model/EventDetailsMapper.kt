package com.hiteshchopra.marketo.data.remote.model

import com.hiteshchopra.marketo.data.remote.mapper.EntityMapper
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity

class EventDetailsMapper : EntityMapper<EventDetailsEntity, EventDetailsResponse> {
    override fun mapToDomain(data: EventDetailsResponse): EventDetailsEntity {
        return EventDetailsEntity(count = data.count,
            next = data.next,
            overflow = data.overflow,
            previous = data.previous,
            results = data.results?.map { result ->
                EventDetailsEntity.Result(
                    aviationRank = result?.aviationRank,
                    brandSafe = result?.brandSafe,
                    category = result?.category,
                    country = result?.country,
                    description = result?.description,
                    duration = result?.duration,
                    end = result?.end,
                    entities = result?.entities?.map { resultEntity ->
                        EventDetailsEntity.Result.Entity(
                            entityId = resultEntity?.entityId,
                            formattedAddress = resultEntity?.formattedAddress,
                            name = resultEntity?.name,
                            type = resultEntity?.type
                        )
                    },
                    firstSeen = result?.firstSeen,
                    geo = EventDetailsEntity.Result.Geo(
                        geometry = EventDetailsEntity.Result.Geo.Geometry(
                            coordinates = result?.geo?.geometry?.coordinates,
                            type = result?.geo?.geometry?.type,
                        ),
                        placekey = result?.geo?.placekey,
                    ),
                    id = result?.id,
                    labels = result?.labels,
                    localRank = result?.localRank,
                    location = result?.location,
                    phqAttendance = result?.phqAttendance,
                    placeHierarchies = result?.placeHierarchies,
                    private = result?.private,
                    rank = result?.rank,
                    relevance = result?.relevance,
                    scope = result?.scope,
                    start = result?.start,
                    state = result?.state,
                    timezone = result?.timezone,
                    title = result?.title,
                    updated = result?.updated
                )
            })
    }

    override fun mapToData(domain: EventDetailsEntity): EventDetailsResponse {
        TODO("Not yet implemented")
    }
}