package ru.gb.android.workshop2.presentation.list.product

sealed class ProductListState {
    object Loading : ProductListState()
    data class Loaded(val productList: List<ProductVO>) : ProductListState()
    object Empty : ProductListState()
    data class Error(val message: String) : ProductListState()
}
