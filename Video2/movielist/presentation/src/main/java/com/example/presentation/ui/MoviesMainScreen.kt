package com.example.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.common.MovieTabs
import com.example.presentation.MovieViewModel

@Composable
fun MoviesMainScreen(viewModel: MovieViewModel) {
    var selectedTab by rememberSaveable { mutableStateOf(com.example.core.common.MovieTabs.MoviesList) }

    Column(modifier = Modifier.fillMaxSize()) {
        when (selectedTab) {
            MovieTabs.MoviesList -> MovieListScreen(viewModel, Modifier.weight(1f))
            MovieTabs.Favorites -> FavoritesScreen(viewModel, Modifier.weight(1f))
        }

        MoviesTabScreen(
            selectedTab = selectedTab,
            onTabSelected = { tab -> selectedTab = tab },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MovieListScreen(viewModel: MovieViewModel, modifier: Modifier = Modifier) {
    val movieList by viewModel.movieListLiveData.observeAsState(emptyList())

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(movieList.size) { index ->
            val movie = movieList[index]
            MovieItem(
                movie = movie,
                onMovieClick = { imdbID -> viewModel.onMovieClick(imdbID) },
                onFavoriteClick = { movie -> viewModel.toggleFavorite(movie) }
            )
        }
    }
}

@Composable
fun MoviesTabScreen(
    selectedTab: com.example.core.common.MovieTabs,
    onTabSelected: (com.example.core.common.MovieTabs) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = modifier
    ) {
        com.example.core.common.MovieTabs.values().forEach { tab ->
            Tab(
                text = { Text(tab.title) },
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}
