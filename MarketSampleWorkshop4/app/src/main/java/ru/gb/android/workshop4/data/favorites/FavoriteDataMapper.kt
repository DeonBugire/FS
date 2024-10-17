package ru.gb.android.workshop4.data.favorites

import ru.gb.android.workshop4.domain.favorites.Favorite

class FavoriteDataMapper {
    fun mapToDomain(entity: FavoriteEntity): Favorite {
        return Favorite(
            id = entity.id
        )
    }
}