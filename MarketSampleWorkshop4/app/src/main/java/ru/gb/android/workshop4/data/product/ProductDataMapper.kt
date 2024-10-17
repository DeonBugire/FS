package ru.gb.android.workshop4.data.product

import javax.inject.Inject

class ProductDataMapper @Inject constructor() {

    // Преобразование DTO в сущность
    fun toEntity(productDto: ProductDto): ProductEntity {
        return ProductEntity(
            id = productDto.id,
            name = productDto.name,
            image = productDto.image,
            price = productDto.price
        )
    }

    fun fromEntity(productEntity: ProductEntity): ProductDto {
        return ProductDto(
            id = productEntity.id,
            name = productEntity.name,
            image = productEntity.image,
            price = productEntity.price
        )
    }
}
