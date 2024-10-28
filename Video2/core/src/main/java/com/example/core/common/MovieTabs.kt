package com.example.core.common

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable


enum class MovieTabs(val title: String) {
    MoviesList("Movies"),
    Favorites("Favorites")
}

@Composable
fun MoviesTabScreen(
    onTabSelected: (MovieTabs) -> Unit
) {
    val selectedTabIndex = rememberSaveable { mutableIntStateOf(MovieTabs.MoviesList.ordinal) }

    TabRow(selectedTabIndex = selectedTabIndex.intValue) {
        MovieTabs.entries.forEachIndexed { index, tab ->
            Tab(
                selected = selectedTabIndex.intValue == index,
                onClick = {
                    selectedTabIndex.intValue = index
                    onTabSelected(MovieTabs.entries[index])
                },
                text = { Text(tab.title) }
            )
        }
    }
}