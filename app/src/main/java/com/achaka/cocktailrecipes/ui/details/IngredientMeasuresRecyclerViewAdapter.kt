package com.achaka.cocktailrecipes.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.R
import com.achaka.cocktailrecipes.databinding.DetailsRecyclerViewItemBinding
import com.achaka.cocktailrecipes.domain.model.IngredientMeasureItem
import com.achaka.cocktailrecipes.domain.model.Units
import java.lang.ClassCastException

class IngredientMeasuresRecyclerViewAdapter(private val onIngredientClick: OnIngredientClick) :
    ListAdapter<IngredientMeasureItem, IngredientMeasuresRecyclerViewAdapter.IngredientMeasureItemViewHolder>(
        DetailsDiffUtil()
    ) {
    inner class IngredientMeasureItemViewHolder(val binding: DetailsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IngredientMeasureItem) {
            binding.ingredientName.setOnClickListener {
                onIngredientClick.onIngredientClick(binding.ingredientName.text.toString())
            }
            binding.ingredientName.text = item.ingredientName
            if (item.measure != null) {
                binding.measure.text = item.measure.toString()
            } else if (item.range != null) {
                try {
                    binding.measure.text = itemView.context.getString(
                        R.string.item_range,
                        (item.range as Pair<Double, Double>).first,
                        (item.range as Pair<Double, Double>).second
                    )
                } catch (cce: ClassCastException) {
                    binding.measure.text = ""
                }
            } else {
                binding.measure.text = item.measureString
            }
            if (item.range != null || item.measure != null)
                binding.units.text =
                    if (item.unit.first != Units.NONE) item.unit.first.abbrev else item.unit.second
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