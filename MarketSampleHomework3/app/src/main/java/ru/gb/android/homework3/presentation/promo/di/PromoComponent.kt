package ru.gb.android.homework3.presentation.promo.di

import dagger.Component
import ru.gb.android.homework3.presentation.promo.PromoListFragment
import javax.inject.Scope

@PromoScope
@Component(
        dependencies = [PromoDependencies::class]
        )
interface PromoComponent {

    @Component.Factory
    interface Factory {
        fun create(promoDependencies: PromoDependencies): PromoComponent
    }
    fun inject(promoListFragment : PromoListFragment)
}
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PromoScope
