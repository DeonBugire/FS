package ru.gb.android.homework3.domain.promo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gb.android.homework3.data.promo.PromoRepository
import javax.inject.Inject

class ConsumePromosUseCase @Inject constructor(
    private val promoRepository: PromoRepository,
    private val promoDomainMapper: PromoDomainMapper
) {
    operator fun invoke(): Flow<List<Promo>> {
        return promoRepository.consumePromos()
            .map { promos -> promos.map(promoDomainMapper::fromEntity) }
    }
}
