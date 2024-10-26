package com.example.di

import com.example.core.di.CoreComponent
import com.example.presentation.MovieDetailsFragment
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [MovieDetailsModule::class, MovieDetailsViewModelModule::class, com.example.di.MovieFavoritesModule::class])
interface MovieDetailsComponent {
    fun inject(fragment: MovieDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            coreComponent: CoreComponent
        ): MovieDetailsComponent
    }
}