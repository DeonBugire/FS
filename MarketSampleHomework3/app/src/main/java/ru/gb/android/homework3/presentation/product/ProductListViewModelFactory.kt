package ru.gb.android.homework3.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.gb.android.homework3.domain.product.ConsumeProductsUseCase
import ru.gb.android.homework3.domain.promo.ConsumePromosUseCase

class ProductListViewModelFactory(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productStateFactory: ProductStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        when {
            modelClass.isAssignableFrom(ProductListViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProductListViewModel(
                    consumeProductsUseCase = consumeProductsUseCase,
                    productStateFactory = productStateFactory,
                    consumePromosUseCase = consumePromosUseCase,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}