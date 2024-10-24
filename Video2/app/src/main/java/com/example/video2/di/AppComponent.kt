package com.example.video2.di

import android.content.Context
import com.example.video2.feature.movielist.presentation.MoviesMainFragment
import com.example.video2.feature.moviedetail.presentation.MovieDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelModule::class,
    MovieDetailsModule::class,
    MovieFavoritesModule::class])
interface AppComponent {
    fun inject(moviesMainFragment: MoviesMainFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}