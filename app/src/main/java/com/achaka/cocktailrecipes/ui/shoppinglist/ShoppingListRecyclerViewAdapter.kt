package com.achaka.cocktailrecipes.ui.shoppinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.ShoppingListRecyclerViewItemBinding
import com.achaka.cocktailrecipes.data.database.entities.ShoppingListItem

class ShoppingListRecyclerViewAdapter() :
    ListAdapter<ShoppingListItem, ShoppingListRecyclerViewAdapter.ItemViewHolder>(
        ShoppingListDiffUtil()
    ) {

    class ItemViewHolder(val binding: ShoppingListRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingListItem: ShoppingListItem) {
            with(binding) {
                itemName.text = shoppingListItem.itemName
                amount.text = shoppingListItem.amount.first + " " + shoppingListItem.amount.second?.abbrev?.lowercase()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ShoppingListRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}