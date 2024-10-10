package ru.gb.android.workshop2.presentation.list.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class PromoListViewModelFactory(
    private val consumePromosUseCase: ConsumePromosUseCase,
    private val promoVOMapper: PromoVOMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PromoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PromoListViewModel(consumePromosUseCase, promoVOMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}