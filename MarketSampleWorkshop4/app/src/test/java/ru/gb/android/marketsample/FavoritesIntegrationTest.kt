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
        println("Before saving products: ${state.value.map { it.id }}")
        state.value = products
        println("After saving products: ${state.value.map { it.id }}")
    }

    fun addInitialProducts(products: List<ProductEntity>) {
        println("Adding initial products: ${products.map { it.id }}")
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
    private val productLocalDataSource = TestProductLocalDataSource()

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

        val initialProducts = listOf(createProductEntity(id = "1", price = 100.0))
        productLocalDataSource.addInitialProducts(initialProducts)

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
    fun `addFavoriteProduct EXPECT isFavoriteFlagUpdated to true`() = runTest(UnconfinedTestDispatcher()) {
        // arrange
        val productEntity = createProductEntity(id = "1", price = 100.0)
        productsFromServer(ProductDataMapper().fromEntity(productEntity))
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
        println("State in test: ${results.map { it.productListState.map { state -> state.id to state.isFavorite } }}")
        val updatedProductState = results.last().productListState.first()
        val expectedUpdatedState = create(id = "1", price = "100,00", isFavorite = true)
        assertEquals(expectedUpdatedState.isFavorite, updatedProductState.isFavorite)
        job.cancel()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `removeFavoriteProduct EXPECT isFavoriteFlagUpdated to false`() = runTest(UnconfinedTestDispatcher()) {
        val productEntity1 = createProductEntity(id = "1", price = 100.0)
        val productEntity2 = createProductEntity(id = "2", price = 200.0)
        productsFromServer(ProductDataMapper().fromEntity(productEntity1), ProductDataMapper().fromEntity(productEntity2))
        sut.addToFavorites(productEntity2.id)
        advanceUntilIdle()
        val (job, results) = collectResults()

        // act
        println("Test: Removing product from favorites")
        sut.removeFromFavorites(productEntity2.id)
        ioDispatcher.scheduler.runCurrent()
        advanceUntilIdle()
        sut.observeFavorites()
        ioDispatcher.scheduler.runCurrent()
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()
        advanceUntilIdle()

        // assert
        println("State in test: ${results.map { it.productListState.map { state -> state.id to state.isFavorite } }}")
        val updatedProductState = results.last().productListState.first { it.id == "2" }
        val expectedUpdatedState = create(id = "2", price = "200,00", isFavorite = false)

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