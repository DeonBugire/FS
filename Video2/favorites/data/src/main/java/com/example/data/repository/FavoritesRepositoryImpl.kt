package com.example.data.repository

import com.example.data.FavoritesDataSource
import com.example.data.mapper.FavoriteDataMapper
import com.example.domain.model.Favorite
import com.example.domain.repository.FavoritesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDataSource: FavoritesDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val mapper: FavoriteDataMapper
) : FavoritesRepository {

    override fun consumeFavorites(): Flow<List<Favorite>> {
        return favoritesDataSource.consumeFavorites()
            .map { entities -> entities.map { mapper.mapToDomain(it) } }
            .flowOn(dispatcher)
    }

    override suspend fun addToFavorites(favorite: Favorite) = withContext(dispatcher) {
        val entity = mapper.mapToEntity(favorite)
        favoritesDataSource.saveFavorite(entity)
    }

    override suspend fun removeFromFavorites(favorite: Favorite) = withContext(dispatcher) {
        val entity = mapper.mapToEntity(favorite)
        favoritesDataSource.removeFavorite(entity)
    }
}