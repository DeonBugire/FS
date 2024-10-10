package ru.gb.android.homework3.data.product

class ProductRemoteDataSource(
    private val productApiService: ProductApiService,
) {
    suspend fun getProducts(): List<ProductDto> {
        return productApiService.getProducts()
    }
}
