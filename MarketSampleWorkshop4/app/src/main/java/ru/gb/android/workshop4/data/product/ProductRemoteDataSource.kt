package ru.gb.android.workshop4.data.product

interface ProductRemoteDataSource {
    suspend fun getProducts(): List<ProductDto>
}

class ProductRemoteDataSourceImpl (
    private val productApiService: ProductApiService,
): ProductRemoteDataSource {
    override suspend fun getProducts(): List<ProductDto> {
        return productApiService.getProducts()
    }
}
