package ru.gb.android.homework3.presentation.promo

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
object PromoModule {

    @Provides
    fun providePromoRepository(
        remoteDataSource: PromoRemoteDataSource,
        localDataSource: PromoLocalDataSource,
        mapper: PromoDataMapper,
        dispatcher: CoroutineDispatcher
    ): PromoRepository {
        return PromoRepository(localDataSource, remoteDataSource, mapper, dispatcher)
    }

    @Provides
    fun provideConsumePromosUseCase(
        promoRepository: PromoRepository,
        mapper: PromoDomainMapper
    ): ConsumePromosUseCase {
        return ConsumePromosUseCase(promoRepository, mapper)
    }
}