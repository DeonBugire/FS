package com.example.domain

import com.example.domain.repository.FavoritesRepository
import com.example.domain.model.Favorite
import com.example.domain.usecase.AddToFavoritesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class AddToFavoritesUseCaseTest {

    private lateinit var repository: FavoritesRepository
    private lateinit var addToFavoritesUseCase: AddToFavoritesUseCase

    @Before
    fun setUp() {
        repository = mock(FavoritesRepository::class.java)
        addToFavoritesUseCase = AddToFavoritesUseCase(repository)
    }

    @Test
    fun `execute should add favorite to repository`() = runBlocking {
        val favorite = Favorite("movie123")

        addToFavoritesUseCase.execute(favorite)

        verify(repository).addToFavorites(favorite)
        verifyNoMoreInteractions(repository)
    }
}