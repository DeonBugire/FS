package ru.gb.android.homework3.data.promo

import kotlinx.serialization.Serializable

@Serializable
class PromoEntity(
    val id: String,
    val name: String,
    val image: String,
    val discount: Double,
    val description: String,
    val type: String,
    val products: List<String>,
)