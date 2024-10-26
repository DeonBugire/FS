package com.example.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.presentation.model.MoviePresentation

@Composable
fun MovieItem(
    movie: MoviePresentation,
    onMovieClick: (String) -> Unit,
    onFavoriteClick: (MoviePresentation) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onMovieClick(movie.imdbID) }
    ) {
        androidx.compose.foundation.Image(
            painter = rememberAsyncImagePainter(movie.poster),
            contentDescription = movie.title,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(text = movie.title, modifier = Modifier.padding(bottom = 8.dp))

            Button(
                onClick = { onFavoriteClick(movie) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = if (movie.isFavorite) "Remove from Favorites" else "Add to Favorites")
            }
        }
    }
}