package com.example.video2.feature.moviefavorites.data.mapper

import com.example.video2.feature.moviefavorites.data.model.FavoriteEntity
import com.example.video2.feature.moviefavorites.domain.model.Favorite

class FavoriteDataMapper {
    fun mapToDomain(entity: FavoriteEntity): Favorite {
        return Favorite(id = entity.id)
    }

    fun mapToEntity(domain: Favorite): FavoriteEntity {
        return FavoriteEntity(id = domain.id)
    }
}