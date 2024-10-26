package com.example.di

import com.example.core.di.CoreComponent
import com.example.presentation.MoviesMainFragment
import dagger.Component

@Component(dependencies = [CoreComponent::class], modules = [MovieListModule::class])
interface MovieListComponent {
    fun inject(fragment: MoviesMainFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): MovieListComponent
    }
}