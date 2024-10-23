package com.example.video2.feature.movielist.presentation

import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.video2.VideoApp
import com.example.video2.feature.moviedetail.presentation.MovieDetailsFragment
import javax.inject.Inject

class MoviesMainFragment : Fragment() {
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
                MoviesMainScreen(viewModel = viewModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as VideoApp).appComponent.inject(this)

        viewModel.navigateToMovieDetails.observe(this, Observer { imdbID ->
            imdbID?.let {
                navigateToMovieDetails(it)
                viewModel.onMovieDetailsNavigated()
            }
        })
        viewModel.searchMovies("Guardians")
    }

    private fun navigateToMovieDetails(imdbID: String) {
        val fragment = MovieDetailsFragment.newInstance(imdbID)
        parentFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }
}