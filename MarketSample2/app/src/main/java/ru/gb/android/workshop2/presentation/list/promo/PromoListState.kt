package ru.gb.android.workshop2.presentation.list.promo

sealed class PromoListState {
    object Loading : PromoListState()
    data class Loaded(val promoList: List<PromoVO>) : PromoListState()
    object Error : PromoListState()
}