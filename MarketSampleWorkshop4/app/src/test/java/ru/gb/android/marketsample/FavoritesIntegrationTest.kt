package ru.gb.android.marketsample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.data.favorites.FavoritesDataSource
import ru.gb.android.workshop4.data.favorites.FavoritesRepository
import ru.gb.android.workshop4.data.product.ProductDataMapper
import ru.gb.android.workshop4.data.product.ProductDto
import ru.gb.android.workshop4.data.product.ProductEntity
import ru.gb.android.workshop4.data.product.ProductLocalDataSource
import ru.gb.android.workshop4.data.product.ProductRemoteDataSource
import ru.gb.android.workshop4.data.product.ProductRepository
import ru.gb.android.workshop4.domain.favorites.AddFavoriteUseCase
import ru.gb.android.workshop4.domain.favorites.ConsumeFavoritesUseCase
import ru.gb.android.workshop4.domain.favorites.RemoveFavoriteUseCase
import ru.gb.android.workshop4.domain.product.ConsumeProductsUseCase
import ru.gb.android.workshop4.domain.product.ProductDomainMapper
import ru.gb.android.workshop4.presentation.common.PriceFormatterImpl
import ru.gb.android.workshop4.presentation.product.ProductListViewModel
import ru.gb.android.workshop4.presentation.product.ProductState
import ru.gb.android.workshop4.presentation.product.ProductStateFactory
import ru.gb.android.workshop4.presentation.product.ProductsScreenState

class TestProductLocalDataSource : ProductLocalDataSource {
    private val state = MutableStateFlow<List<ProductEntity>>(listOf())
    override fun consumeProducts(): Flow<List<ProductEntity>> = state.asStateFlow()
    override suspend fun saveProducts(products: List<ProductEntity>) {
        state.value = products
    }
}

class TestFavoriteDataSource : FavoritesDataSource {
    private val state = MutableStateFlow<List<FavoriteEntity>>(listOf())

    override fun consumeFavorites(): Flow<List<FavoriteEntity>> = state.asStateFlow()

    override suspend fun saveFavorite(favoriteEntity: FavoriteEntity) {
        println("Before saveFavorite: ${state.value.map { it.id }}")
        state.value += favoriteEntity
        println("After saveFavorite: ${state.value.map { it.id }}")
    }

    override suspend fun removeFavorite(favoriteEntity: FavoriteEntity) {
        state.value = state.value.filter { it.id != favoriteEntity.id }
    }
}


@RunWith(MockitoJUnitRunner::class)
class FavoritesIntegrationTest {
    private lateinit var sut: ProductListViewModel
    private val testFavoriteDataSource = TestFavoriteDataSource()

    @Mock
    lateinit var productRemoteDataSource: ProductRemoteDataSource

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())
    private val ioDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        val productRepository = ProductRepository(
            productLocalDataSource = TestProductLocalDataSource(),
            productRemoteDataSource = productRemoteDataSource,
            productDataMapper = ProductDataMapper(),
            dispatcher = ioDispatcher,
        )
        val consumeProductsUseCase = ConsumeProductsUseCase(
            productRepository = productRepository,
            productDomainMapper = ProductDomainMapper(),
        )
        val favoritesRepository = FavoritesRepository(
            favoritesDataSource = testFavoriteDataSource,
            dispatcher = ioDispatcher,
        )
        val consumeFavoritesUseCase = ConsumeFavoritesUseCase(
            favoritesRepository = favoritesRepository
        )
        val addFavoriteUseCase = AddFavoriteUseCase(favoritesRepository)
        val removeFavoriteUseCase = RemoveFavoriteUseCase(favoritesRepository)
        sut = ProductListViewModel(
            consumeProductsUseCase = consumeProductsUseCase,
            consumeFavoritesUseCase = consumeFavoritesUseCase,
            addFavoriteUseCase = addFavoriteUseCase,
            removeFavoriteUseCase = removeFavoriteUseCase,
            productStateFactory = ProductStateFactory(priceFormatter = PriceFormatterImpl())
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `requestProducts EXPECT show all three states`() = runTest(UnconfinedTestDispatcher()) {
        // arrange
        val productDtoList = listOf(
            ProductDataMapper().fromEntity(createProductEntity(id = "1", price = 100.0)),
            ProductDataMapper().fromEntity(createProductEntity(id = "2", price = 200.0))
        )

        productsFromServer(*productDtoList.toTypedArray())

        val expectedInitialState = ProductsScreenState()
        val expectedLoadingState = ProductsScreenState(isLoading = true)
        val expectedDataState = ProductsScreenState(
            isLoading = false,
            productListState = listOf(
                create(id = "1", price = "100,00"),
                create(id = "2", price = "200,00")
            )
        )
        val (job, results) = collectResults()

        // act
        ioDispatcher.scheduler.runCurrent()
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        // assert
        assertEquals(3, results.size)
        assertEquals(expectedInitialState, results[0])
        assertEquals(expectedLoadingState, results[1])
        assertEquals(expectedDataState, results[2])
        job.cancel()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `addFavoriteProduct EXPECT isFavoriteFlagUpdated`() = runTest(UnconfinedTestDispatcher()) {
        // arrange
        val productEntity = createProductEntity(id = "1", price = 100.0)
        productsFromServer(ProductDataMapper().fromEntity(productEntity))

        val expectedInitialState = ProductsScreenState()
        val expectedLoadingState = ProductsScreenState(isLoading = true)
        val expectedDataState = ProductsScreenState(
            isLoading = false,
            productListState = listOf(
                create(id = "1", price = "100,00", isFavorite = true)
            )
        )
        val (job, results) = collectResults()

        // act
        println("Test: Adding product to favorites")
        sut.addToFavorites(productEntity.id)
        ioDispatcher.scheduler.runCurrent()
        advanceUntilIdle()
        println("Test: Running observeFavorites")
        sut.observeFavorites()
        ioDispatcher.scheduler.runCurrent()
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()
        advanceUntilIdle()

        // assert
        assertEquals(4, results.size)
        assertEquals(expectedInitialState, results[0])
        assertEquals(expectedLoadingState, results[1])
        assertEquals(expectedDataState, results[2])
        val updatedProductState = results[3].productListState.last()
        val expectedUpdatedState = create(id = "1", price = "100,00", isFavorite = true)

        assertEquals(expectedUpdatedState.isFavorite, updatedProductState.isFavorite)

        job.cancel()
    }


    private suspend fun productsFromServer(vararg products: ProductDto) {
        whenever(productRemoteDataSource.getProducts()).thenReturn(products.toList())
    }

    private fun createProductEntity(
        id: String,
        price: Double
    ): ProductEntity {
        return ProductEntity(
            id = id,
            name = "Product $id",
            image = "image_url",
            price = price
        )
    }


    private fun create(
        id: String,
        name: String = "Product $id",
        image: String = "image_url",
        price: String = "100.00",
        isFavorite: Boolean = false
    ): ProductState {
        return ProductState(
            id = id,
            name = name,
            image = image,
            price = price,
            isFavorite = isFavorite
        )
    }

    private fun CoroutineScope.collectResults(): Pair<Job, List<ProductsScreenState>> {
        val results = mutableListOf<ProductsScreenState>()
        val job = sut.state
            .onEach(results::add)
            .launchIn(this)

        return (job to results)
    }
}