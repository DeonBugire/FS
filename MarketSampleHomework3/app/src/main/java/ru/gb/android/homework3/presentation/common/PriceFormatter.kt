package ru.gb.android.homework3.presentation.common

import java.text.DecimalFormat

class PriceFormatter {

    companion object {
        const val PATTERN = "#,##0.00"
    }

    fun format(price: Double): String {
        val decimalFormat = DecimalFormat(PATTERN)
        return decimalFormat.format(price)
    }
}
