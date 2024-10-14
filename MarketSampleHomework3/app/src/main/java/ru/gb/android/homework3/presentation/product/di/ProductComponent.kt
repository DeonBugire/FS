package ru.gb.android.homework3.presentation.product.di

import dagger.Subcomponent
import ru.gb.android.homework3.presentation.product.ProductListFragment
import javax.inject.Scope

@ProductScope
@Subcomponent
interface ProductComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProductComponent
    }
    fun inject(productListFragment : ProductListFragment)
}
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProductScope