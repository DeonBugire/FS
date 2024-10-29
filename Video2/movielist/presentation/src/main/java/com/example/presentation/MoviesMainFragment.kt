package com.example.presentation

import android.content.Context
import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.di.ViewModelFactory
import com.example.core.di.findDependencies
import com.example.core.navigation.Navigator
import com.example.presentation.ui.MoviesMainScreen
import javax.inject.Inject
import com.example.presentation.di.DaggerMoviesMainComponent


class MoviesMainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MovieViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var navigator: Navigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerMoviesMainComponent.factory()
            .create(findDependencies())
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigateToMovieDetails.observe(this) { imdbID ->
            imdbID?.let {
                navigator.navigateToMovieDetails(requireActivity(), it)
                viewModel.onMovieDetailsNavigated()
            }
        }
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MoviesMainScreen(viewModel = viewModel)
            }
        }.also {
            viewModel.refreshMovies()
            viewModel.refreshFavorites()
        }
    }
}
