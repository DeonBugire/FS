package ru.gb.android.homework3.presentation.product

import ru.gb.android.homework3.ServiceLocator

object FeatureServiceLocator {

    fun provideViewModelFactory(): ProductListViewModelFactory {
        return ProductListViewModelFactory(
            consumeProductsUseCase = ServiceLocator.provideConsumeProductsUseCase(),
            productStateFactory = provideProductVOFactory(),
            consumePromosUseCase = ServiceLocator.provideConsumePromosUseCase(),
        )
    }

    private fun provideProductVOFactory(): ProductStateFactory {
        return ProductStateFactory(
            discountFormatter = ServiceLocator.provideDiscountFormatter(),
            priceFormatter = ServiceLocator.providePriceFormatter(),
        )
    }
}