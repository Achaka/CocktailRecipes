package com.achaka.cocktailrecipes.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.MainRecyclerViewItemBinding
import com.achaka.cocktailrecipes.domain.model.Drink
import com.achaka.cocktailrecipes.domain.model.DrinkItem
import com.achaka.cocktailrecipes.domain.model.UserDrink
import com.bumptech.glide.RequestManager

class SearchHorizontalAdapter(
    private val glide: RequestManager,
    private val onItemClick: OnItemClick
) :
    ListAdapter<Drink, SearchHorizontalAdapter.DrinkViewHolder>(SearchDiffUtil()) {

    inner class DrinkViewHolder(val binding: MainRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.drinkCard.setOnClickListener {
                onItemClick.openDetails(drink = currentList[bindingAdapterPosition])
            }
        }

        fun bind(drink: DrinkItem) {
            when (drink) {
                is Drink -> {
                    glide.load(drink.thumbUrl)
                        .centerCrop()
                        .into(binding.cardImage)
                    binding.cardName.text = drink.name
                }
                is UserDrink -> {
                    binding.cardName.text = drink.name
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding = MainRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}