package ru.gb.android.homework3.presentation.promo

import ru.gb.android.homework3.ServiceLocator

object FeatureServiceLocator {

    fun provideViewModelFactory(): PromoListViewModelFactory {
        return PromoListViewModelFactory(
            promoStateFactory = providePromoStateFactory(),
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase(),
        )
    }

    private fun providePromoStateFactory(): PromoStateFactory {
        return PromoStateFactory()
    }
}