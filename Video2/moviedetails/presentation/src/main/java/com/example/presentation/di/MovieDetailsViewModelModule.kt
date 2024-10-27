package com.example.presentation.di

import androidx.lifecycle.ViewModel
import com.example.core.di.ViewModelKey
import com.example.presentation.viewmodel.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieDetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel
}