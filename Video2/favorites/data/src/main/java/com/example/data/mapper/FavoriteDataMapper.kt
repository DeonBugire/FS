package com.example.data.mapper

import com.example.data.model.FavoriteEntity
import com.example.domain.model.Favorite

class FavoriteDataMapper {
    fun mapToDomain(entity: FavoriteEntity): Favorite {
        return Favorite(id = entity.id)
    }

    fun mapToEntity(domain: Favorite): FavoriteEntity {
        return FavoriteEntity(id = domain.id)
    }
}