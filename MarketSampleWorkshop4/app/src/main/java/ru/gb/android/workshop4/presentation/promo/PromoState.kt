package ru.gb.android.workshop4.presentation.promo

data class PromoScreenState(
    val isLoading: Boolean = false,
    val promoListState: List<PromoState> = emptyList(),
    val hasError: Boolean = false,
    val errorRes: Int = 0,
)

data class PromoState(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
)