package com.example.animelist.presentation

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.example.animelist.presentation.model.MoviePresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "favorites")

@Composable
fun FavoritesScreen(viewModel: MovieViewModel) {
    val context = LocalContext.current
    val favoriteMovies = remember { mutableStateOf(emptyList<MoviePresentation>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getFavorites(context).collect {
            favoriteMovies.value = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (favoriteMovies.value.isEmpty()) {
            Text("Your favorites list is empty.")
        } else {
            LazyColumn {
                items(favoriteMovies.value) { movie ->
                    MovieItem(movie, onMovieClick = {})
                }
            }
        }
    }
}

fun MovieViewModel.getFavorites(context: Context): Flow<List<MoviePresentation>> {
    return context.dataStore.data.map { preferences ->
        val favoriteIds = preferences[stringSetPreferencesKey("favorite_movies")] ?: emptySet()
        this.movieListLiveData.value?.filter { it.imdbID in favoriteIds } ?: emptyList()
    }
}
