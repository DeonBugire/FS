package com.example.video2.di

import android.content.Context
import com.example.core.di.CoreComponent
import com.example.presentation.MovieDetailsFragment
import com.example.video2.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(moviesMainFragment: MoviesMainFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            coreComponent: CoreComponent
        ): AppComponent
    }
}
