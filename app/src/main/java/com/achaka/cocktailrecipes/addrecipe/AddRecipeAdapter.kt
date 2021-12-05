package com.achaka.cocktailrecipes.addrecipe

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.AddIngredientRecyclerViewItemBinding
import com.achaka.cocktailrecipes.model.domain.IngredientMeasureItem
import com.achaka.cocktailrecipes.model.domain.Units

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

    inner class IngredientMeasureItemViewHolder(private val binding: AddIngredientRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IngredientMeasureItem) {
            binding.addIngredientEditText.text.append(item.ingredientName)
            binding.addIngredientEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    item.ingredientName = p0.toString()
                }
            })
            binding.addMeasureEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    item.measure = p0.toString().toDouble()
                }
            })
            binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    item.unit = Pair(Units.CL, "")
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    item.unit = Pair(Units.NONE, "")
                }

            }
            if (item.measure != null) {
                binding.addMeasureEditText.text.append(item.measure.toString())
            } else binding.addMeasureEditText.text.append("")
        }
    }
}