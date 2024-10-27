package com.example.presentation

import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.di.ViewModelFactory
import com.example.core.di.findDependencies
import com.example.presentation.ui.MoviesMainScreen
import javax.inject.Inject
import com.example.presentation.di.DaggerMoviesMainComponent


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
        DaggerMoviesMainComponent.factory()
            .create(findDependencies())
            .inject(this)
    }
}