package ru.gb.android.workshop4.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import ru.gb.android.workshop4.data.favorites.FavoritesDataSource
import ru.gb.android.workshop4.data.favorites.FavoritesDataSourceImpl
import ru.gb.android.workshop4.data.product.ProductApiService
import ru.gb.android.workshop4.data.product.ProductLocalDataSource
import ru.gb.android.workshop4.data.product.ProductLocalDataSourceImpl
import ru.gb.android.workshop4.data.product.ProductRemoteDataSource
import ru.gb.android.workshop4.data.product.ProductRemoteDataSourceImpl
import ru.gb.android.workshop4.data.promo.PromoApiService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideProductApiService(
        retrofit: Retrofit
    ): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePromoApiService(
        retrofit: Retrofit
    ): PromoApiService {
        return retrofit.create(PromoApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app")

    @Singleton
    @Provides
    fun provideDataStoreOfPreferences(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.appDataStore
    }

    @Singleton
    @Provides
    fun provideProductLocalDataSource(
        dataStore: DataStore<Preferences>
    ): ProductLocalDataSource {
        return ProductLocalDataSourceImpl(
            dataStore = dataStore
        )
    }

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(
        productApiService: ProductApiService,
    ): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(
            productApiService = productApiService
        )
    }

    @Singleton
    @Provides
    fun provideFavoritesDataSource(
        dataStore: DataStore<Preferences>,
    ): FavoritesDataSource {
        return FavoritesDataSourceImpl(
            dataStore = dataStore
        )
    }
}