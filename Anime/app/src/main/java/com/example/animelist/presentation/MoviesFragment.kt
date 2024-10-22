package com.example.animelist.presentation

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.animelist.AnimeApp
import com.example.animelist.presentation.model.MoviePresentation
import javax.inject.Inject

class MoviesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MoviesViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MoviesTabScreen(viewModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as AnimeApp).appComponent.inject(this)
        viewModel.navigateToMovieDetails.observe(this) { imdbID ->
            imdbID?.let {
                navigateToMovieDetails(it)
            }
        }
    }

    private fun navigateToMovieDetails(imdbID: String) {
        val fragment = MovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putString("imdbID", imdbID)
            }
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }
}


@Composable
fun MoviesTabScreen(viewModel: MoviesViewModel) {
    val tabIndex = viewModel.tabIndex.observeAsState(0)

    Column {
        TabRow(selectedTabIndex = tabIndex.value) {
            Tab(text = { Text("Movies") }, selected = tabIndex.value == 0, onClick = { viewModel.setTab(0) })
            Tab(text = { Text("Favorites") }, selected = tabIndex.value == 1, onClick = { viewModel.setTab(1) })
        }

        when (tabIndex.value) {
            0 -> MovieListScreen(viewModel)
            1 -> FavoriteListScreen(viewModel)
        }
    }
}

@Composable
fun MovieListScreen(viewModel: MoviesViewModel) {
    val movieList by viewModel.movieListLiveData.observeAsState(emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movieList) { movie ->
            MovieItem(movie = movie, onMovieClick = { imdbID ->
                viewModel.onMovieClick(imdbID)
            }, onFavoriteClick = {
                viewModel.toggleFavorite(movie.imdbID)
            })
        }
    }
}
@Composable
fun FavoriteListScreen(viewModel: MoviesViewModel) {
    val favoriteList by viewModel.favoriteListLiveData.observeAsState(emptyList<MoviePresentation>())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(favoriteList) { movie ->
            MovieItem(movie = movie, onMovieClick = { imdbID ->
                viewModel.onMovieClick(imdbID)
            }, onFavoriteClick = {
                viewModel.toggleFavorite(movie.imdbID)
            })
        }
    }
}


@Composable
fun MovieItem(movie: MoviePresentation, onMovieClick: (String) -> Unit, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onMovieClick(movie.imdbID) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.poster),
            contentDescription = movie.title,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .padding(end = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Year: ${movie.year}", style = MaterialTheme.typography.bodySmall)
            // Heart icon for favorite
            FavoriteIcon(isFavorite = movie.isFavorite, onFavoriteClick = onFavoriteClick)
        }
    }
}

@Composable
fun FavoriteIcon(isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    val icon = if (isFavorite) com.example.animelist.R.drawable.ic_heart_filled else com.example.animelist.R.drawable.ic_heart_outline
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "Favorite",
        tint = if (isFavorite) Color.Red else Color.Gray,
        modifier = Modifier
            .size(24.dp)
            .clickable { onFavoriteClick() }
    )
}