package com.achaka.cocktailrecipes.search

import androidx.recyclerview.widget.DiffUtil
import com.achaka.cocktailrecipes.model.domain.Drink

class SearchDiffUtil: DiffUtil.ItemCallback<Drink>() {
    override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
        return oldItem.id == newItem.id
    }
}