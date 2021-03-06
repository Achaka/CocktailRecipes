package com.achaka.cocktailrecipes.ui.addrecipe

import androidx.recyclerview.widget.DiffUtil
import com.achaka.cocktailrecipes.domain.model.IngredientMeasureItem

class AddRecipeDiffUtil() : DiffUtil.ItemCallback<IngredientMeasureItem>() {
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
        return ((oldItem.ingredientName == newItem.ingredientName) &&
                (oldItem.measure == newItem.measure) &&
                (oldItem.unit == newItem.unit))
    }

}