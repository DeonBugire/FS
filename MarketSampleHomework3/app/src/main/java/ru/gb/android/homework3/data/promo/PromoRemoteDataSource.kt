package ru.gb.android.homework3.data.promo

import javax.inject.Inject

class PromoRemoteDataSource @Inject constructor(
    private val promoApiService: PromoApiService,
) {
    suspend fun getPromos(): List<PromoDto> {
        return promoApiService.getPromos()
    }
}
