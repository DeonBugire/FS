package com.example.animelist.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.animelist.data.api.MovieApi
import com.example.animelist.data.mapper.MovieDetailsMapper
import com.example.animelist.data.mapper.MovieMapper
import com.example.animelist.domain.model.Movie
import com.example.animelist.domain.model.MovieDetails
import com.example.animelist.domain.model.FavoriteMovie
import com.example.animelist.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "favorites")

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val context: Context
) : MovieRepository {

    private val FAVORITES_KEY = stringSetPreferencesKey("favorite_movies")

    override fun searchMovies(title: String): Single<List<Movie>> {
        return api.searchMovies(title)
            .map { response -> MovieMapper.mapToDomainList(response.searchResults) }
    }

    override fun getMovieDetails(imdbID: String): Single<MovieDetails> {
        return api.getMovieDetails(imdbID)
            .map { response -> MovieDetailsMapper.mapToDomain(response) }
    }

    override fun isFavorite(imdbID: String): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            val favoriteIds = preferences[FAVORITES_KEY] ?: emptySet()
            imdbID in favoriteIds
        }
    }

    override fun getFavoriteMovies(): Flow<List<FavoriteMovie>> {
        return context.dataStore.data.map { preferences ->
            val favoriteIds = preferences[FAVORITES_KEY] ?: emptySet()
            favoriteIds.map { imdbID -> FavoriteMovie(imdbID) }
        }
    }

    override suspend fun addMovieToFavorites(imdbID: String) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = currentFavorites + imdbID
        }
    }

    override suspend fun removeMovieFromFavorites(imdbID: String) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = currentFavorites - imdbID
        }
    }
}