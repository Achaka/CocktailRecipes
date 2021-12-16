package com.achaka.cocktailrecipes.ui.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.MainRecyclerViewItemBinding
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.ui.search.OnItemClick
import com.bumptech.glide.RequestManager

class MainRecyclerViewAdapter(
    private val glide: RequestManager,
    private val onItemClick: OnItemClick
) :
    ListAdapter<DrinkItem, MainRecyclerViewAdapter.MainRecyclerViewHolder>(
        (MainRecyclerViewDiffUtil())
    ) {

    inner class MainRecyclerViewHolder(private val binding: MainRecyclerViewItemBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val binding =
            MainRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}