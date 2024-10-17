package ru.gb.android.workshop4.presentation.promo

import dagger.hilt.android.scopes.ViewModelScoped
import ru.gb.android.workshop4.domain.promo.Promo
import javax.inject.Inject

@ViewModelScoped
class PromoStateFactory @Inject constructor() {
    fun map(promo: Promo): PromoState {
        return PromoState(
            id = promo.id,
            name = promo.name,
            image = promo.image,
            description = promo.description,
        )
    }
}
