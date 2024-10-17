package ru.gb.android.workshop4.presentation.product

import dagger.hilt.android.scopes.ViewModelScoped
import ru.gb.android.workshop4.domain.product.Product
import ru.gb.android.workshop4.presentation.common.PriceFormatter
import javax.inject.Inject
import ru.gb.android.workshop4.data.favorites.FavoriteEntity

@ViewModelScoped
class ProductStateFactory @Inject constructor(
    private val priceFormatter: PriceFormatter
) {
    fun create(product: Product, favoriteEntities: List<FavoriteEntity>): ProductState {
        val isFavorite = favoriteEntities.any { it.id == product.id }
        return ProductState(
            id = product.id,
            name = product.name,
            image = product.image,
            price = priceFormatter.format(product.price),
            isFavorite = isFavorite
        )
    }
}