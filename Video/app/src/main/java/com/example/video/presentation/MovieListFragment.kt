package com.example.video.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import coil.compose.rememberAsyncImagePainter
import com.example.video.VideoApp
import com.example.video.presentation.model.MoviePresentation
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
                MovieListScreen(viewModel)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as VideoApp).appComponent.inject(this)
        viewModel.searchMovies("Guardians")
    }
}
@Composable
fun MovieListScreen(viewModel: MovieViewModel) {
    val movieList by viewModel.movieListLiveData.observeAsState(emptyList())
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movieList) { movie ->
            MovieItem(movie)
        }
    }
}
@Composable
fun MovieItem(movie: MoviePresentation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
            Text(
                text = movie.title,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}