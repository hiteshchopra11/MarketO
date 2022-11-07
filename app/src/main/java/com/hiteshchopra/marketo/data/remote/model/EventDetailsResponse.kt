package com.hiteshchopra.marketo.data.remote.model

import com.google.gson.annotations.SerializedName
import com.hiteshchopra.marketo.data.remote.mapper.DataModel

data class EventDetailsResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("overflow")
    val overflow: Boolean?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<Result?>?
) : DataModel() {
    data class Result(
        @SerializedName("aviation_rank")
        val aviationRank: Any?,
        @SerializedName("brand_safe")
        val brandSafe: Boolean?,
        @SerializedName("category")
        val category: String?,
        @SerializedName("country")
        val country: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("duration")
        val duration: Int?,
        @SerializedName("end")
        val end: String?,
        @SerializedName("entities")
        val entities: List<Entity?>?,
        @SerializedName("first_seen")
        val firstSeen: String?,
        @SerializedName("geo")
        val geo: Geo?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("labels")
        val labels: List<String?>?,
        @SerializedName("local_rank")
        val localRank: Int?,
        @SerializedName("location")
        val location: List<Double?>?,
        @SerializedName("phq_attendance")
        val phqAttendance: Int?,
        @SerializedName("place_hierarchies")
        val placeHierarchies: List<List<String?>?>?,
        @SerializedName("private")
        val `private`: Boolean?,
        @SerializedName("rank")
        val rank: Int?,
        @SerializedName("relevance")
        val relevance: Double?,
        @SerializedName("scope")
        val scope: String?,
        @SerializedName("start")
        val start: String?,
        @SerializedName("state")
        val state: String?,
        @SerializedName("timezone")
        val timezone: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("updated")
        val updated: String?
    ) {
        data class Entity(
            @SerializedName("entity_id")
            val entityId: String?,
            @SerializedName("formatted_address")
            val formattedAddress: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("type")
            val type: String?
        )

        data class Geo(
            @SerializedName("geometry")
            val geometry: Geometry?,
            @SerializedName("placekey")
            val placekey: String?
        ) {
            data class Geometry(
                @SerializedName("coordinates")
                val coordinates: List<Double?>?,
                @SerializedName("type")
                val type: String?
            )
        }
    }
}