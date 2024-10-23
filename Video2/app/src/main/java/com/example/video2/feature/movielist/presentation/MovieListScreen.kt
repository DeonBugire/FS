package com.example.video2.feature.movielist.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun MovieListScreen(viewModel: MovieViewModel) {
    val movieListState = viewModel.movieListLiveData.observeAsState(emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        val movieList = movieListState.value
        items(movieList.size) { index ->
            val movie = movieList[index]
            MovieItem(movie) { imdbID ->
                viewModel.onMovieClick(imdbID)
            }
        }
    }
}
