package com.example.video2.feature.movielist.presentation

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
import com.example.video2.common.MovieTabs
import com.example.video2.feature.movielist.presentation.model.MoviePresentation

@Composable
fun MoviesMainScreen(viewModel: MovieViewModel) {
    var selectedTab by rememberSaveable { mutableStateOf(MovieTabs.MoviesList) }

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
            MovieItem(movie) { imdbID ->
                viewModel.onMovieClick(imdbID)
            }
        }
    }
}

@Composable
fun FavoritesScreen(viewModel: MovieViewModel, modifier: Modifier = Modifier) {
    Text(
        text = "No favorites yet.",
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun MoviesTabScreen(
    selectedTab: MovieTabs,
    onTabSelected: (MovieTabs) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = modifier
    ) {
        MovieTabs.values().forEach { tab ->
            Tab(
                text = { Text(tab.title) },
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}
