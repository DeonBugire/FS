package ru.gb.android.homework3.presentation.product

import ru.gb.android.homework3.domain.product.Product
import ru.gb.android.homework3.domain.promo.Promo
import ru.gb.android.homework3.presentation.common.DiscountFormatter
import ru.gb.android.homework3.presentation.common.PriceFormatter
import javax.inject.Inject

interface ProductStateFactory {
    fun create(product: Product, promos: List<Promo>): ProductState
}

class ProductStateFactoryImpl @Inject constructor(
    private val discountFormatter: DiscountFormatter,
    private val priceFormatter: PriceFormatter,
) : ProductStateFactory{
    override fun create(product: Product, promos: List<Promo>): ProductState {
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId == product.id })
        }
        return ProductState(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price.let(priceFormatter::format),
            hasDiscount = promoForProduct != null,
            discount = promoForProduct.resolveDiscount(),
        )
    }

    private fun Promo?.resolveDiscount(): String {
        return (this as? Promo.PromoForProducts)
            ?.discount
            ?.toInt()
            ?.let(discountFormatter::format)
            ?: ""
    }
}