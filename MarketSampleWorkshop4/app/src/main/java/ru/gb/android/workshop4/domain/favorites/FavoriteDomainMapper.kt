package ru.gb.android.workshop4.domain.favorites

import ru.gb.android.workshop4.presentation.favorites.FavoritePresentationModel

class FavoriteDomainMapper {
    fun mapToPresentation(domainModel: Favorite): FavoritePresentationModel {
        return FavoritePresentationModel(
            id = domainModel.id
        )
    }
}