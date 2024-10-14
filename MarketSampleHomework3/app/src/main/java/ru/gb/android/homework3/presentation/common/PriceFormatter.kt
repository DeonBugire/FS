package ru.gb.android.homework3.presentation.common

import java.text.DecimalFormat
import javax.inject.Inject

class PriceFormatter @Inject constructor(){

    companion object {
        const val PATTERN = "#,##0.00"
    }

    fun format(price: Double): String {
        val decimalFormat = DecimalFormat(PATTERN)
        return decimalFormat.format(price)
    }
}
