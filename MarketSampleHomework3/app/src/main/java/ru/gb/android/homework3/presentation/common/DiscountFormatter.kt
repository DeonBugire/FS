package ru.gb.android.homework3.presentation.common

class DiscountFormatter {
    fun format(discount: Int): String {
        return String.format("%d %%", discount)
    }
}