package com.achaka.cocktailrecipes.addrecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.AddIngredientRecyclerViewItemBinding
import com.achaka.cocktailrecipes.model.domain.IngredientMeasureItem

class AddRecipeAdapter :
    ListAdapter<IngredientMeasureItem, AddRecipeAdapter.IngredientMeasureItemViewHolder>(
        AddRecipeDiffUtil()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientMeasureItemViewHolder {
        val binding = AddIngredientRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientMeasureItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: IngredientMeasureItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IngredientMeasureItemViewHolder(val binding: AddIngredientRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IngredientMeasureItem) {
            binding.addIngredientEditText.text.append(item.ingredientName)
            binding.addMeasureEditText.text.append(item.measure)
        }
    }

}