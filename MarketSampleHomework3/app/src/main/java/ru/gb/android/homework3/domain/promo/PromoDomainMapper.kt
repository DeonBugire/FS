package ru.gb.android.homework3.domain.promo

import ru.gb.android.homework3.data.promo.PromoEntity
import javax.inject.Inject

class PromoDomainMapper @Inject constructor(){
    fun fromEntity(promoEntity: PromoEntity): Promo {
        return if (promoEntity.type == "product") {
            Promo.PromoForProducts(
                id = promoEntity.id,
                name = promoEntity.name,
                image = promoEntity.image,
                description = promoEntity.description,
                discount = promoEntity.discount,
                products = promoEntity.products,
            )
        } else {
            Promo.PromoForPrice(
                id = promoEntity.id,
                name = promoEntity.name,
                image = promoEntity.image,
                description = promoEntity.description,
                discount = promoEntity.discount,
            )
        }
    }
}
