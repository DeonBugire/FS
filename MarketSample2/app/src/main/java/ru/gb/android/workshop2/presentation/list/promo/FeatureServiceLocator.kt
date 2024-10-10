package ru.gb.android.workshop2.presentation.list.promo

import ru.gb.android.workshop2.ServiceLocator

object FeatureServiceLocator {

    fun providePromoListViewModel(): PromoListViewModel {
        return PromoListViewModel(
            promoVOMapper = providePromoVOMapper(),
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase()
        )
    }

    fun providePromoVOMapper(): PromoVOMapper {
        return PromoVOMapper()
    }
}