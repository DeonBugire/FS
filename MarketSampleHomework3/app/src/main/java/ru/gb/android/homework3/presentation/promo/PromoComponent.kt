package ru.gb.android.homework3.presentation.promo

import dagger.Component
import ru.gb.android.homework3.di.AppComponent
import javax.inject.Scope

@PromoScope
@Component(dependencies = [AppComponent::class], modules = [PromoModule::class])
interface PromoComponent {
    fun inject(fragment: PromoListFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): PromoComponent
    }
}
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PromoScope
