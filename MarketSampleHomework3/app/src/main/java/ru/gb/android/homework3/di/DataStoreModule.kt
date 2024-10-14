package ru.gb.android.homework3.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.gb.android.homework3.data.product.ProductApiService
import ru.gb.android.homework3.data.promo.PromoApiService
import javax.inject.Singleton

@Module
object DataStoreModule {

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app")

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return context.appDataStore
    }

    @Provides
    fun provideProductApiService(
        retrofit: Retrofit,
    ): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }
    @Provides
    fun providePromoApiService(
        retrofit: Retrofit
    ): PromoApiService {
        return retrofit.create(PromoApiService::class.java)
    }
}