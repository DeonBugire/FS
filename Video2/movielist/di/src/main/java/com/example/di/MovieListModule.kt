package com.example.di

import com.example.data.api.MovieApi
import com.example.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class MovieListModule {
    @Provides
    fun provideMovieApi(okHttpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(com.example.data.api.MovieApi::class.java)
    }

    @Provides
    fun provideMovieRepository(api: com.example.data.api.MovieApi): MovieRepository =
        com.example.data.repository.MovieRepositoryImpl(api)
}