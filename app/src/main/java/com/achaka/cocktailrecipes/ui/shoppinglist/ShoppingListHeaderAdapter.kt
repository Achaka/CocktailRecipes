package com.achaka.cocktailrecipes.ui.shoppinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.ShoppingListHeaderLayoutBinding

class ShoppingListHeaderAdapter :
    RecyclerView.Adapter<ShoppingListHeaderAdapter.ShoppingListHeaderViewHolder>() {

    inner class ShoppingListHeaderViewHolder(private val binding: ShoppingListHeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingListHeaderViewHolder {
        val binding = ShoppingListHeaderLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShoppingListHeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingListHeaderViewHolder, position: Int) {
        //empty
    }

    override fun getItemCount(): Int = 1
}