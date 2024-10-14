package ru.gb.android.homework3.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import ru.gb.android.homework3.MarketSampleApp
import ru.gb.android.homework3.presentation.product.di.ProductComponent
import ru.gb.android.homework3.presentation.promo.di.PromoDependencies
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DataStoreModule::class,
        DispatchersModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent : PromoDependencies {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context : Context): AppComponent
    }
    fun productComponentFactory(): ProductComponent.Factory

    fun inject(application: MarketSampleApp)
}