package ru.gb.android.workshop2.presentation.list.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase
import java.net.UnknownHostException

class ProductListViewModel(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productVOFactory: ProductVOFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<ProductListState>()
    val state: LiveData<ProductListState> = _state

    fun loadProducts() {
        viewModelScope.launch {
            _state.value = ProductListState.Loading
            try {
                combine(
                    consumeProductsUseCase(),
                    consumePromosUseCase(),
                ) { products, promos ->
                    products.map { product -> productVOFactory.create(product, promos) }
                }
                    .collect { productListVO ->
                        _state.value = ProductListState.Loaded(productListVO)
                    }
            } catch (exception: Exception) {
                val errorMessage = when (exception) {
                    is UnknownHostException -> "Нет соединения с интернетом."
                    else -> "Произошла ошибка: ${exception.message}"
                }
                _state.value = ProductListState.Error(errorMessage)
            }
        }
    }

    fun refreshProducts() {
        loadProducts()
    }
}