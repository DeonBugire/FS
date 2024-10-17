package ru.gb.android.workshop4.domain.promo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gb.android.workshop4.data.promo.PromoRepository
import javax.inject.Inject

class ConsumePromosUseCase @Inject constructor(
    private val promoRepository: PromoRepository,
    private val promoDomainMapper: PromoDomainMapper,
) {
    operator fun invoke(): Flow<List<Promo>> {
        return promoRepository.consumePromos()
            .map { promos -> promos.map(promoDomainMapper::fromEntity) }
    }
}