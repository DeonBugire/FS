package com.example.di

import com.example.core.di.CoreComponent
import com.example.data.repository.FavoritesRepositoryImpl
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [MovieFavoritesModule::class]
)
interface FavoritesComponent {
    fun inject(repository: FavoritesRepositoryImpl)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): FavoritesComponent
    }
}

