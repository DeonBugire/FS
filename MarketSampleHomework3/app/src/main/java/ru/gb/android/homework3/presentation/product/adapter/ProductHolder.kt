package ru.gb.android.homework3.presentation.product.adapter

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.gb.android.homework3.marketsample.R
import ru.gb.android.homework3.marketsample.databinding.ItemProductBinding
import ru.gb.android.homework3.presentation.product.ProductState

class ProductHolder(
    private val binding: ItemProductBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productState: ProductState) {
        binding.image.load(productState.image)
        binding.name.text = productState.name
        binding.price.text =
            binding.root.resources.getString(R.string.price_with_arg, productState.price)
        if (productState.hasDiscount) {
            binding.promo.visibility = VISIBLE
            binding.promo.text = productState.discount
        } else {
            binding.promo.visibility = GONE
        }
    }
}
