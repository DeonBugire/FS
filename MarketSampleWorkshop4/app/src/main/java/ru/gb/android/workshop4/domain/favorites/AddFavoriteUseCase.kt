package ru.gb.android.workshop4.domain.favorites

import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.data.favorites.FavoritesRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend fun execute(favorite: FavoriteEntity) {
        favoritesRepository.addToFavorites(favorite)
    }
}

