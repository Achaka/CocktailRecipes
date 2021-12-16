package com.achaka.cocktailrecipes.ui.addrecipe

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.AddIngredientRecyclerViewItemBinding
import com.achaka.cocktailrecipes.model.database.entities.determineUnit
import com.achaka.cocktailrecipes.model.domain.IngredientMeasureItem
import com.achaka.cocktailrecipes.model.domain.Units

class AddRecipeAdapter :
    ListAdapter<IngredientMeasureItem, AddRecipeAdapter.IngredientMeasureItemViewHolder>(
        AddRecipeDiffUtil()
    ) {

    private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientMeasureItemViewHolder {
        val binding = AddIngredientRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val unitsArray = Units.values().map { it.abbrev }
        spinnerAdapter = ArrayAdapter(parent.context, android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter.addAll(unitsArray)

        return IngredientMeasureItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: IngredientMeasureItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IngredientMeasureItemViewHolder(private val binding: AddIngredientRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.addIngredientEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    currentList[bindingAdapterPosition].ingredientName = p0.toString()
                }
            })

            binding.addMeasureEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    currentList[bindingAdapterPosition].measure = p0.toString().toDouble()
                }
            })

            binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    currentList[bindingAdapterPosition].unit =
                        Pair(determineUnit(spinnerAdapter.getItem(p2).toString()), "")
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    currentList[bindingAdapterPosition].unit = Pair(Units.NONE, "")
                }
            }

            binding.spinner.adapter = spinnerAdapter
        }

        fun bind(item: IngredientMeasureItem) {
            binding.addIngredientEditText.text.append(item.ingredientName)
            if (item.measure != null) {
                binding.addMeasureEditText.text.append(item.measure.toString())
            } else binding.addMeasureEditText.text.append("")
        }
    }
}