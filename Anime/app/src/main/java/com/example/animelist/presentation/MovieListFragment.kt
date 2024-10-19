package com.example.animelist.presentation

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import coil.compose.rememberAsyncImagePainter
import com.example.animelist.AnimeApp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.os.bundleOf
import com.example.animelist.presentation.model.MoviePresentation
import javax.inject.Inject

class MovieListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MovieViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieListScreen(viewModel, onMovieClick = { imdbID ->
                    // Переход на MovieDetailsFragment при клике
                    val fragment = MovieDetailsFragment().apply {
                        arguments = bundleOf("imdbID" to imdbID)
                    }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(android.R.id.content, fragment)
                        .addToBackStack(null)
                        .commit()
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as AnimeApp).appComponent.inject(this)
        viewModel.searchMovies("Guardians")
    }
}

@Composable
fun MovieListScreen(viewModel: MovieViewModel, onMovieClick: (String) -> Unit) {
    val movieList by viewModel.movieListLiveData.observeAsState(emptyList())

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(movieList) { movie ->
            MovieItem(movie, onMovieClick)
        }
    }
}

@Composable
fun MovieItem(movie: MoviePresentation, onMovieClick: (String) -> Unit) {
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
        }
    }
}

