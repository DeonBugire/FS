package ru.gb.android.workshop4.presentation.common

import javax.inject.Inject

class DiscountFormatter @Inject constructor() {
    fun format(discount: Int): String {
        return String.format("%d %%", discount)
    }
}