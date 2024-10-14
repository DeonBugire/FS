package ru.gb.android.homework3.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import ru.gb.android.homework3.domain.product.ConsumeProductsUseCase
import ru.gb.android.homework3.domain.promo.ConsumePromosUseCase
import ru.gb.android.homework3.marketsample.R

class ProductListViewModel(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productStateFactory: ProductStateFactoryImpl,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsScreenState())
    val state: StateFlow<ProductsScreenState> = _state.asStateFlow()

    init {
        requestProducts()
    }

    private fun requestProducts() {
        combine(
            consumeProductsUseCase(),
            consumePromosUseCase(),
        ) { products, promos ->
            products.map { product -> productStateFactory.create(product, promos) }
        }
            .onStart {
                _state.update { screenState -> screenState.copy(isLoading = true) }
            }
            .onEach { productListState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        productListState = productListState,
                    )
                }
            }
            .catch {
                _state.update { screenState ->
                    screenState.copy(
                        hasError = true,
                        errorProvider = { context -> context.getString(R.string.error_wile_loading_data) }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        requestProducts()
    }

    fun errorHasShown() {
        _state.update { screenState -> screenState.copy(hasError = false) }
    }
}
