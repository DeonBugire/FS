package com.example.video2.di

import android.content.Context
import com.example.core.di.Dependencies
import com.example.core.di.NetworkModule
import com.example.di.MovieFavoritesModule
import com.example.presentation.MovieDetailsFragment
import com.example.presentation.MoviesMainFragment
import com.example.presentation.di.MovieDetailsModule
import com.example.presentation.di.MovieListModule
import com.example.video2.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        MovieFavoritesModule::class,
        MovieDetailsModule::class,
        MovieListModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : Dependencies {

    fun inject(mainActivity: MainActivity)
    fun inject(moviesMainFragment: MoviesMainFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}