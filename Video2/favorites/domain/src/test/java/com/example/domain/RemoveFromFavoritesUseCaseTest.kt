package com.example.domain

import com.example.domain.repository.FavoritesRepository
import com.example.domain.model.Favorite
import com.example.domain.usecase.RemoveFromFavoritesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class RemoveFromFavoritesUseCaseTest {

    private lateinit var repository: FavoritesRepository
    private lateinit var removeFromFavoritesUseCase: RemoveFromFavoritesUseCase

    @Before
    fun setUp() {
        repository = mock(FavoritesRepository::class.java)
        removeFromFavoritesUseCase = RemoveFromFavoritesUseCase(repository)
    }

    @Test
    fun `execute should remove favorite from repository`() = runBlocking {
        val favorite = Favorite("movie123")

        removeFromFavoritesUseCase.execute(favorite)

        verify(repository).removeFromFavorites(favorite)
        verifyNoMoreInteractions(repository)
    }
}