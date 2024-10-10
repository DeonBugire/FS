package ru.gb.android.homework3.presentation.promo

import ru.gb.android.homework3.domain.promo.Promo

class PromoStateFactory {
    fun map(promo: Promo): PromoState {
        return PromoState(
            id = promo.id,
            name = promo.name,
            image = promo.image,
            description = promo.description,
        )
    }
}
