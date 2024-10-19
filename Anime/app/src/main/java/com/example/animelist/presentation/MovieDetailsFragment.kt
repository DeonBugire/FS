package com.example.animelist.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import coil.compose.rememberAsyncImagePainter
import com.example.animelist.AnimeApp
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MovieDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MovieDetailsScreen(viewModel, onBack = {
                    requireActivity().supportFragmentManager.popBackStack()
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as AnimeApp).appComponent.inject(this)

        val imdbID = requireArguments().getString("imdbID")
        if (imdbID == null) {
            Log.e("MovieDetailsFragment", "imdbID is null")
            return
        }
        Log.d("MovieDetailsFragment", "Received imdbID: $imdbID")

        viewModel.getMovieDetails(imdbID)
    }
}

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel, onBack: () -> Unit) {
    val movieDetailsState = viewModel.movieDetailsLiveData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        movieDetailsState.value?.let { details ->

            Image(
                painter = rememberAsyncImagePainter(details.poster),
                contentDescription = details.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = details.title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = "Released", value = details.released)
            InfoRow(label = "Genre", value = details.genre)
            InfoRow(label = "Director", value = details.director)
            InfoRow(label = "Actors", value = details.actors)
            InfoRow(label = "Plot", value = details.plot)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { onBack() }) {
                Text(text = "Back")
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(2f)
        )
    }
}