package ru.gb.android.homework3.presentation.product

import dagger.Subcomponent
import javax.inject.Scope

@ProductScope
@Subcomponent(modules = [ProductModule::class])
interface ProductComponent {
    fun inject(fragment: ProductListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductComponent
    }
}
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProductScope