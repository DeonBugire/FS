package com.example.di

import com.example.core.di.Dependencies
import com.example.presentation.MovieDetailsFragment

import dagger.Component

@Component(dependencies = [Dependencies::class])
interface MovieDetailsComponent {
    fun inject(fragment: MovieDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(dependencies: Dependencies): MovieDetailsComponent
    }
}