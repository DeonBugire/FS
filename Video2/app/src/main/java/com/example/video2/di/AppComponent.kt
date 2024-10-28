package com.example.video2.di

import android.content.Context
import com.example.core.di.CoreComponent
import com.example.core.di.Dependencies
import com.example.di.MovieFavoritesModule
import com.example.presentation.MovieDetailsFragment
import com.example.presentation.MoviesMainFragment
import com.example.presentation.di.MovieDetailsModule
import com.example.presentation.di.MovieDetailsViewModelModule
import com.example.presentation.di.MovieListModule
import com.example.presentation.di.MovieListViewModelModule
import com.example.video2.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelModule::class,
    MovieFavoritesModule::class,
    MovieDetailsViewModelModule::class,
    MovieDetailsModule::class,
    MovieListViewModelModule::class,
    MovieListModule::class
])
interface AppComponent : Dependencies {

    fun coreComponentFactory(): CoreComponent.Factory

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