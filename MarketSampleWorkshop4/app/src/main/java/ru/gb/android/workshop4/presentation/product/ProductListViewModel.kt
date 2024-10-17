package ru.gb.android.workshop4.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.domain.favorites.AddFavoriteUseCase
import ru.gb.android.workshop4.domain.favorites.ConsumeFavoritesUseCase
import ru.gb.android.workshop4.domain.favorites.RemoveFavoriteUseCase
import ru.gb.android.workshop4.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop4.domain.product.Product
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val consumeFavoritesUseCase: ConsumeFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val productStateFactory: ProductStateFactory,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsScreenState())
    val state: StateFlow<ProductsScreenState> get() = _state.asStateFlow()
    fun setProductList(products: List<ProductState>) {
        _state.value = _state.value.copy(productListState = products)
    }

    private var productList: List<Product> = emptyList()
    private var favoriteList: List<FavoriteEntity> = emptyList()

    init {
        observeProducts()
        observeFavorites()
    }

    fun observeProducts() {
        consumeProductsUseCase()
            .onStart {
                _state.update { it.copy(isLoading = true) }
            }
            .onEach { products ->
                productList = products
                updateProductListState()
            }
            .catch {
                _state.update { it.copy(isLoading = false, hasError = true) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeFavorites() {
        consumeFavoritesUseCase.execute()
            .onEach { favorites ->
                favoriteList = favorites
                updateProductListState()
            }
            .launchIn(viewModelScope)
    }

    private fun updateProductListState() {
        val updatedProductListState = productList.map { product ->
            productStateFactory.create(product, favoriteList)
        }
        _state.update { it.copy(isLoading = false, productListState = updatedProductListState) }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val newProducts = consumeProductsUseCase().first()
            productList = newProducts
            updateProductListState()
        }
    }

    fun addToFavorites(favoriteId: String) {
        viewModelScope.launch {
            addFavoriteUseCase.execute(FavoriteEntity(favoriteId))
        }
    }

    fun removeFromFavorites(favoriteId: String) {
        viewModelScope.launch {
            removeFavoriteUseCase.execute(FavoriteEntity(favoriteId))
        }
    }

    fun errorHasShown() {
        _state.update { it.copy(hasError = false) }
    }
}