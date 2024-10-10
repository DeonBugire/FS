package ru.gb.android.workshop2.presentation.list.product

import ru.gb.android.workshop2.ServiceLocator

object FeatureServiceLocator {

    fun provideProductVOFactory(): ProductVOFactory {
        return ProductVOFactory(
            discountFormatter = ServiceLocator.provideDiscountFormatter(),
            priceFormatter = ServiceLocator.providePriceFormatter(),
        )
    }
}