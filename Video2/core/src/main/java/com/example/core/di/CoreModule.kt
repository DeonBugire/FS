package com.example.core.di

import androidx.fragment.app.Fragment
import com.example.core.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CoreModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator {
        return object : Navigator {
            override fun getMoviesMainFragment(): Fragment {
                throw UnsupportedOperationException("Необходимо предоставить реализацию Navigator")
            }

            override fun getMovieDetailsFragment(imdbID: String): Fragment {
                throw UnsupportedOperationException("Необходимо предоставить реализацию Navigator")
            }
        }
    }
}
