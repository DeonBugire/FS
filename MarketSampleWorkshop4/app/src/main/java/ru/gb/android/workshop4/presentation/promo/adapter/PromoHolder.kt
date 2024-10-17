package ru.gb.android.workshop4.presentation.promo.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.android.workshop4.marketsample.databinding.ItemPromoBinding
import ru.gb.android.workshop4.presentation.promo.PromoState

class PromoHolder(
    private val binding: ItemPromoBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(promoState: PromoState) {
        binding.image.load(promoState.image)
        binding.name.text = promoState.name
        binding.description.text = promoState.description
    }
}
