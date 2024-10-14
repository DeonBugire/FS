package ru.gb.android.homework3.domain.product

import ru.gb.android.homework3.data.product.ProductDto
import ru.gb.android.homework3.data.product.ProductEntity
import javax.inject.Inject

class ProductDomainMapper @Inject constructor() {
    fun fromDto(productDto: ProductDto): Product {
        return Product(
            id = productDto.id,
            name = productDto.name,
            image = productDto.image,
            price = productDto.price
        )
    }

    fun fromEntity(productEntity: ProductEntity): Product {
        return Product(
            id = productEntity.id,
            name = productEntity.name,
            image = productEntity.image,
            price = productEntity.price
        )
    }

    fun toEntity(product: Product): ProductEntity {
        return ProductEntity(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price
        )
    }
}