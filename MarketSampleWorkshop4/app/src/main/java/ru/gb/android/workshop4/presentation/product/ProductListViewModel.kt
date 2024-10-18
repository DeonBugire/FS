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

    private fun observeProducts() {
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

    fun observeFavorites() {
        consumeFavoritesUseCase.execute()
            .onEach { favorites ->
                favoriteList = favorites
                updateProductListState()
            }
            .launchIn(viewModelScope)
    }

    private fun updateProductListState() {
        println("Updating product list state with favoriteList: ${favoriteList.map { it.id }}")
        val updatedProductListState = productList.map { product ->
            val productState = productStateFactory.create(product, favoriteList)
            println("ProductStateFactory: Created product state for ${product.id}, isFavorite: ${productState.isFavorite}")
            productState
        }
        _state.update {
            println("updateProductListState: Final updated product list state - ${updatedProductListState.map { it.id to it.isFavorite }}")
            it.copy(isLoading = false, productListState = updatedProductListState)
        }
        println("State after update: ${_state.value.productListState.map { it.id to it.isFavorite }}")
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
            println("ViewModel: Adding product to favorites with id: $favoriteId")
            addFavoriteUseCase.execute(FavoriteEntity(favoriteId))
            observeFavorites()
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