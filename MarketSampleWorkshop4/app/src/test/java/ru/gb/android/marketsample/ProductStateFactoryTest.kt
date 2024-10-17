package ru.gb.android.marketsample

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import ru.gb.android.workshop4.domain.product.Product
import ru.gb.android.workshop4.data.favorites.FavoriteEntity
import ru.gb.android.workshop4.presentation.common.PriceFormatter
import ru.gb.android.workshop4.presentation.product.ProductStateFactory
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


class ProductStateFactoryTest {

    lateinit var sut: ProductStateFactory

    @Mock
    lateinit var priceFormatter: PriceFormatter

    private val product = Product(id = "1", name = "Test Product", image = "url", price = 99.99)
    private val favoriteEntity = FavoriteEntity(id = "1")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = ProductStateFactory(priceFormatter)
    }

    @Test
    fun `create WHEN product is in favorites EXPECT product state with isFavorite true`() {
        // Arrange
        val favorites = listOf(favoriteEntity)
        whenever(priceFormatter.format(product.price)).thenReturn("99.99")

        // Act
        val result = sut.create(product, favorites)

        // Assert
        assertEquals(true, result.isFavorite)
        assertEquals(product.id, result.id)
        assertEquals(product.name, result.name)
        assertEquals(product.image, result.image)
        assertEquals("99.99", result.price)
    }

    @Test
    fun `create WHEN product is not in favorites EXPECT product state with isFavorite false`() {
        // Arrange
        val favorites = emptyList<FavoriteEntity>()
        whenever(priceFormatter.format(product.price)).thenReturn("99.99")

        // Act
        val result = sut.create(product, favorites)

        // Assert
        assertEquals(false, result.isFavorite)
        assertEquals(product.id, result.id)
        assertEquals(product.name, result.name)
        assertEquals(product.image, result.image)
        assertEquals("99.99", result.price)
    }
}
