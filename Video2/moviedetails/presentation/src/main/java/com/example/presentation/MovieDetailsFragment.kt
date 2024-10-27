package com.example.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.compose.ui.platform.ComposeView
import com.example.presentation.ui.MovieDetailsScreen
import com.example.presentation.viewmodel.MovieDetailsViewModel
import com.example.core.di.ViewModelFactory
import javax.inject.Inject
import com.example.core.di.findDependencies

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
                val imdbID = requireArguments().getString("imdbID") ?: ""
                MovieDetailsScreen(
                    viewModel = viewModel,
                    imdbID = imdbID,
                    onBack = { requireActivity().supportFragmentManager.popBackStack() },
                    onFavoriteClick = { movieDetails ->
                        viewModel.toggleFavorite(movieDetails)
                    }
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerMovieDetailsComponent.factory()
            .create(findDependencies())
            .inject(this)
    }

    companion object {
        fun newInstance(imdbID: String): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val args = Bundle()
            args.putString("imdbID", imdbID)
            fragment.arguments = args
            return fragment
        }
    }
}