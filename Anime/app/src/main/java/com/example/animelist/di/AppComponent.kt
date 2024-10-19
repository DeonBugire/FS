package com.example.animelist.di

import com.example.animelist.presentation.MovieDetailsFragment
import com.example.animelist.presentation.MovieListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(movieListFragment: MovieListFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}