package com.example.presentation.di

import com.example.core.di.Dependencies
import com.example.presentation.MoviesMainFragment
import dagger.Component

@Component(dependencies = [Dependencies::class])
interface MoviesMainComponent {
    fun inject(fragment: MoviesMainFragment)

    @Component.Factory
    interface Factory {
        fun create(dependencies: Dependencies): MoviesMainComponent
    }
}