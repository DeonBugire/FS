package ru.gb.android.homework3.presentation.product

import dagger.Module
import dagger.Provides

@Module
object ProductModule {

    @Provides
    fun provideProductRepository(
        remoteDataSource: ProductRemoteDataSource,
        localDataSource: ProductLocalDataSource,
        mapper: ProductDataMapper,
        dispatcher: CoroutineDispatcher
    ): ProductRepository {
        return ProductRepository(localDataSource, remoteDataSource, mapper, dispatcher)
    }

    @Provides
    fun provideConsumeProductsUseCase(
        productRepository: ProductRepository,
        mapper: ProductDomainMapper
    ): ConsumeProductsUseCase {
        return ConsumeProductsUseCase(productRepository, mapper)
    }
}