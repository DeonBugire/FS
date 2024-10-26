package com.example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.data.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import javax.inject.Inject

class FavoritesDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : FavoritesDataSource {

    private val FAVORITES_KEY = stringSetPreferencesKey("favorites_key")

    override fun consumeFavorites(): Flow<List<FavoriteEntity>> {
        return dataStore.data.map { preferences ->
            val favoritesSet = preferences[FAVORITES_KEY] ?: emptySet()
            favoritesSet.map { json -> Json.decodeFromString<FavoriteEntity>(json) }
        }
    }

    override suspend fun saveFavorite(favorite: FavoriteEntity) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = currentFavorites + Json.encodeToString(favorite)
        }
    }

    override suspend fun removeFavorite(favorite: FavoriteEntity) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = currentFavorites - Json.encodeToString(favorite)
        }
    }
}
