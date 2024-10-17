package ru.gb.android.workshop4.domain.favorites

import kotlinx.coroutines.flow.Flow
import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.data.favorites.FavoritesRepository
import javax.inject.Inject

class ConsumeFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    fun execute(): Flow<List<FavoriteEntity>> {
        return favoritesRepository.consumeFavorites()
    }
}
