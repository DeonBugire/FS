package ru.gb.android.homework3.presentation.promo.di

import retrofit2.Retrofit
import ru.gb.android.homework3.domain.promo.ConsumePromosUseCase

interface PromoDependencies {
    fun getConsumePromoUseCase() : ConsumePromosUseCase
}