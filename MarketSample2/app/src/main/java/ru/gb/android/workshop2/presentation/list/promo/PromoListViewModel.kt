package ru.gb.android.workshop2.presentation.list.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.gb.android.workshop2.domain.promo.ConsumePromosUseCase

class PromoListViewModel(
    private val consumePromosUseCase: ConsumePromosUseCase,
    private val promoVOMapper: PromoVOMapper
) : ViewModel() {

    private val _state = MutableStateFlow<PromoListState>(PromoListState.Loading)
    val state: StateFlow<PromoListState> = _state

    fun loadPromos() {
        viewModelScope.launch {
            consumePromosUseCase()
                .map { promos -> promos.map(promoVOMapper::map) }
                .onStart { _state.value = PromoListState.Loading }
                .catch { _state.value = PromoListState.Error }
                .collect { promoList -> _state.value = PromoListState.Loaded(promoList) }
        }
    }

    fun refreshPromos() {
        loadPromos()
    }
}