package com.example.video2.di

import com.example.video2.feature.moviedetail.data.api.MovieDetailsApi
import com.example.video2.feature.moviedetail.data.repository.MovieDetailsRepositoryImpl
import com.example.video2.feature.moviedetail.domain.repository.MovieDetailsRepository
import dagger.Module
import dagger.Provides

@Module
class MovieDetailsModule {

    @Provides
    fun provideMovieDetailsRepository(api: MovieDetailsApi): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(api)
    }
}