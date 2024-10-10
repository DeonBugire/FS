package ru.gb.android.homework3.presentation.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.gb.android.homework3.marketsample.databinding.ItemProductBinding
import ru.gb.android.homework3.presentation.product.ProductState

class ProductsAdapter : ListAdapter<ProductState, ProductHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val entity = getItem(position)
        entity?.let {
            holder.bind(entity)
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<ProductState>() {

    override fun areItemsTheSame(oldItem: ProductState, newItem: ProductState): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductState, newItem: ProductState): Boolean {
        return oldItem == newItem
    }
}
