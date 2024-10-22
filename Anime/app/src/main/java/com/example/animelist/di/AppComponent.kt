package com.example.animelist.di

import android.content.Context
import com.example.animelist.presentation.MovieDetailsFragment
import com.example.animelist.presentation.MovieListFragment
import com.example.animelist.presentation.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, FavoritesModule::class])
interface AppComponent {
    fun inject(movieListFragment: MovieListFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
    fun inject(moviesFragment: MoviesFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}