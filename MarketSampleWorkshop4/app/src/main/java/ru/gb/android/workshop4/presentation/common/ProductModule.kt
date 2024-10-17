package ru.gb.android.workshop4.presentation.common

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface ProductModule {

    @Binds
    fun providePriceFormatter(priceFormatterImpl: PriceFormatterImpl): PriceFormatter
}