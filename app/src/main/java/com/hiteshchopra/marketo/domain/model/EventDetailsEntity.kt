package com.hiteshchopra.marketo.domain.model

import com.hiteshchopra.marketo.domain.mapper.DomainModel

data class EventDetailsEntity(
    val count: Int?,
    val next: String?,
    val overflow: Boolean?,
    val previous: Any?,
    val results: List<Result?>?
) : DomainModel() {
    data class Result(
        val aviationRank: Any?,
        val brandSafe: Boolean?,
        val category: String?,
        val country: String?,
        val description: String?,
        val duration: Int?,
        val end: String?,
        val entities: List<Entity?>?,
        val firstSeen: String?,
        val geo: Geo?,
        val id: String?,
        val labels: List<String?>?,
        val localRank: Int?,
        val location: List<Double?>?,
        val phqAttendance: Int?,
        val placeHierarchies: List<List<String?>?>?,
        val `private`: Boolean?,
        val rank: Int?,
        val relevance: Double?,
        val scope: String?,
        val start: String?,
        val state: String?,
        val timezone: String?,
        val title: String?,
        val updated: String?
    ) {
        data class Entity(
            val entityId: String?,
            val formattedAddress: String?,
            val name: String?,
            val type: String?
        )

        data class Geo(
            val geometry: Geometry?,
            val placekey: String?
        ) {
            data class Geometry(
                val coordinates: List<Double?>?,
                val type: String?
            )
        }
    }
}