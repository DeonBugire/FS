package com.example.animelist.di

import com.example.animelist.presentation.MovieListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(fragment: MovieListFragment)

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}