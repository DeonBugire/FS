package com.example.presentation.di

import com.example.data.api.MovieDetailsApi
import com.example.data.repository.MovieDetailsRepositoryImpl
import com.example.domain.repository.MovieDetailsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class MovieDetailsModule {
    @Provides
    fun provideMovieDetailsApi(okHttpClient: OkHttpClient): MovieDetailsApi {
        return Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(MovieDetailsApi::class.java)
    }

    @Provides
    fun provideMovieDetailsRepository(api: MovieDetailsApi): MovieDetailsRepository = MovieDetailsRepositoryImpl(api)
}
