package com.achaka.cocktailrecipes.ui.shoppinglist

import androidx.recyclerview.widget.DiffUtil
import com.achaka.cocktailrecipes.model.database.entities.ShoppingListItem

class ShoppingListDiffUtil: DiffUtil.ItemCallback<ShoppingListItem>() {
    override fun areItemsTheSame(
        oldItem: ShoppingListItem,
        newItem: ShoppingListItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ShoppingListItem,
        newItem: ShoppingListItem
    ): Boolean {
        return oldItem.itemName == newItem.itemName
    }
}