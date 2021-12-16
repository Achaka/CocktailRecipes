package com.achaka.cocktailrecipes.ui.details

import androidx.recyclerview.widget.DiffUtil
import com.achaka.cocktailrecipes.model.domain.IngredientMeasureItem

class DetailsDiffUtil: DiffUtil.ItemCallback<IngredientMeasureItem>() {
    override fun areItemsTheSame(
        oldItem: IngredientMeasureItem,
        newItem: IngredientMeasureItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: IngredientMeasureItem,
        newItem: IngredientMeasureItem
    ): Boolean {
        return (oldItem.ingredientName == newItem.ingredientName) && (oldItem.measure == oldItem.measure)
    }
}