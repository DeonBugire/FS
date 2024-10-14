package ru.gb.android.homework3.presentation.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.gb.android.homework3.domain.promo.ConsumePromosUseCase
import javax.inject.Inject

class PromoListViewModelFactory @Inject constructor(
    private val promoStateFactory: PromoStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        when {
            modelClass.isAssignableFrom(PromoListViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PromoListViewModel(
                    promoStateFactory = promoStateFactory,
                    consumePromosUseCase = consumePromosUseCase,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}