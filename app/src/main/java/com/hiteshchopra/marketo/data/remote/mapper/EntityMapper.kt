package com.hiteshchopra.marketo.data.remote.mapper

import com.hiteshchopra.marketo.domain.mapper.DomainModel

interface EntityMapper<DoM : DomainModel, DM : DataModel> {
    fun mapToDomain(data: DM): DoM

    fun mapToData(domain: DoM): DM
}

open class DataModel