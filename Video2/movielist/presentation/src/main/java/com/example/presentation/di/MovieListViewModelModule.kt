package com.example.presentation.di

import androidx.lifecycle.ViewModel
import com.example.core.di.ViewModelKey
import com.example.presentation.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(viewModel: MovieViewModel): ViewModel
}