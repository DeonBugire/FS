package com.example.video2.feature.movielist.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen(viewModel: MovieViewModel) {
    Text(text = "No favorites yet.", modifier = Modifier.padding(16.dp))
}