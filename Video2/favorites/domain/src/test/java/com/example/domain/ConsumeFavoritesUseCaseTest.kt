package com.example.domain

import com.example.domain.repository.FavoritesRepository
import com.example.domain.model.Favorite
import com.example.domain.usecase.ConsumeFavoritesUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.Mockito.*

class ConsumeFavoritesUseCaseTest {

    private lateinit var repository: FavoritesRepository
    private lateinit var consumeFavoritesUseCase: ConsumeFavoritesUseCase

    @Before
    fun setUp() {
        repository = mock(FavoritesRepository::class.java)
        consumeFavoritesUseCase = ConsumeFavoritesUseCase(repository)
    }

    @Test
    fun `execute should return list of favorites from repository`() = runBlocking {
        val favorites = listOf(Favorite("movie123"), Favorite("movie456"))
        `when`(repository.consumeFavorites()).thenReturn(flowOf(favorites))

        val result = consumeFavoritesUseCase.execute().toList()

        assertEquals(favorites, result[0])
        verify(repository).consumeFavorites()
        verifyNoMoreInteractions(repository)
    }
}