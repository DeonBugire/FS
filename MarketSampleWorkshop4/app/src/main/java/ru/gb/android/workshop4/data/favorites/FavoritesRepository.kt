package ru.gb.android.workshop4.data.favorites

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val favoritesDataSource: FavoritesDataSource,
    private val dispatcher: CoroutineDispatcher,
) {
    fun consumeFavorites(): Flow<List<FavoriteEntity>> {
        return favoritesDataSource.consumeFavorites()
            .flowOn(dispatcher)
    }

    suspend fun addToFavorites(favorite: FavoriteEntity) = withContext(dispatcher) {
        favoritesDataSource.saveFavorite(favorite)
    }

    suspend fun removeFromFavorites(favorite: FavoriteEntity) = withContext(dispatcher) {
        favoritesDataSource.removeFavorite(favorite)
    }
}