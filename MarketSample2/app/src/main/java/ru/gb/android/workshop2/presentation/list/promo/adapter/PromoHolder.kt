package ru.gb.android.workshop2.presentation.list.promo.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.android.workshop2.marketsample.databinding.ItemPromoBinding
import ru.gb.android.workshop2.presentation.list.promo.PromoVO

class PromoHolder(
    private val binding: ItemPromoBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(promo: PromoVO) {
        binding.image.load(promo.image)
        binding.name.text = promo.name
        binding.description.text = promo.description
    }
}
