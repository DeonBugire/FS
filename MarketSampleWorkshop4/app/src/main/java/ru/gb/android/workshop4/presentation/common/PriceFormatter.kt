package ru.gb.android.workshop4.presentation.common

import java.text.DecimalFormat
import javax.inject.Inject

interface PriceFormatter {
    fun format(price: Double): String
}

class PriceFormatterImpl @Inject constructor(): PriceFormatter {

    companion object {
        const val PATTERN = "#,##0.00"
    }

    override fun format(price: Double): String {
        val decimalFormat = DecimalFormat(PATTERN)
        return decimalFormat.format(price)
    }
}
