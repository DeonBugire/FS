package com.example.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.presentation.model.MovieDetailsPresentation
import com.example.presentation.viewmodel.MovieDetailsViewModel

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel,
    imdbID: String,
    onBack: () -> Unit,
    onFavoriteClick: (MovieDetailsPresentation) -> Unit
) {
    val movieDetails = viewModel.movieDetails.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        movieDetails?.let { details ->
            Image(
                painter = rememberAsyncImagePainter(details.poster),
                contentDescription = details.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 16.dp)
            )
            Text(text = details.title)
            Text(text = "Year: ${details.year}")
            Text(text = "Director: ${details.director}")
            Text(text = "Actors: ${details.actors}")
            Text(text = "Plot: ${details.plot}")

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onBack() }) {
                Text(text = "Back")
            }

            Button(
                onClick = { onFavoriteClick(details) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                val buttonText = if (details.isFavorite) "Remove from Favorites" else "Add to Favorites"
                Text(text = buttonText)
            }
        }
    }
}