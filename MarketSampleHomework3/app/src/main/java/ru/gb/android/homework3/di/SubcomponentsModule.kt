package ru.gb.android.homework3.di

import dagger.Module
import ru.gb.android.homework3.presentation.product.di.ProductComponent

@Module(
    subcomponents = [ProductComponent::class]
)
object SubcomponentsModule