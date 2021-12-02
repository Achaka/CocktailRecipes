package com.achaka.cocktailrecipes.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.DetailsRecyclerViewItemBinding
import com.achaka.cocktailrecipes.model.domain.IngredientMeasureItem
import com.achaka.cocktailrecipes.model.domain.Units

class IngredientMeasuresRecyclerViewAdapter :
    ListAdapter<IngredientMeasureItem, IngredientMeasuresRecyclerViewAdapter.IngredientMeasureItemViewHolder>(
        DetailsDiffUtil()
    ) {
    inner class IngredientMeasureItemViewHolder(val binding: DetailsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IngredientMeasureItem) {
            binding.ingredientName.text = item.ingredientName
            if (item.measure != null) {
                binding.measure.text = item.measure.toString()
            } else binding.measure.text = item.measureString
//            binding.units.text = if (item.unit != null) item.unit.abbrev else Units.NONE.abbrev
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientMeasureItemViewHolder {
        val binding = DetailsRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientMeasureItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientMeasureItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}