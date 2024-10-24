package com.example.video2.feature.movielist.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.video2.feature.movielist.presentation.MovieViewModel

@Composable
fun FavoritesScreen(viewModel: MovieViewModel, modifier: Modifier = Modifier) {
    val favoriteMoviesState = viewModel.favoriteMoviesLiveData.observeAsState(emptyList())

    val favoriteMovies = favoriteMoviesState.value

    if (favoriteMovies.isEmpty()) {
        Text(text = "No favorites yet.", modifier = modifier.padding(16.dp))
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(favoriteMovies.size) { index ->
                val movie = favoriteMovies[index]
                MovieItem(
                    movie = movie,
                    onMovieClick = { imdbID -> viewModel.onMovieClick(imdbID) },
                    onFavoriteClick = { movie -> viewModel.toggleFavorite(movie) }
                )
            }
        }
    }
}