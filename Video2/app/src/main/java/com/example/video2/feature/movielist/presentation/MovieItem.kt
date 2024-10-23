package com.example.video2.feature.movielist.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.video2.feature.movielist.presentation.model.MoviePresentation

@Composable
fun MovieItem(movie: MoviePresentation, onMovieClick: (String) -> Unit) {
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
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(text = movie.title, modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}