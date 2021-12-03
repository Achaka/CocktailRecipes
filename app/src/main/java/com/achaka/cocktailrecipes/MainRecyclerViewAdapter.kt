package com.achaka.cocktailrecipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.MainRecyclerViewItemBinding
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.search.OnItemClick
import com.bumptech.glide.RequestManager

class MainRecyclerViewAdapter (private val glide: RequestManager, private val onItemClick: OnItemClick) :
    ListAdapter<DrinkItem, MainRecyclerViewAdapter.MainRecyclerViewHolder>(
        (MainRecyclerViewDiffUtil())
    ) {

    inner class MainRecyclerViewHolder( private val binding: MainRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: DrinkItem) {
            when (drink) {
                is Drink -> {
                    glide.load(drink.thumbUrl)
                        .centerCrop()
                        .into(binding.cardImage)
                    binding.cardName.text = drink.name
                    binding.drinkCard.setOnClickListener{
                        onItemClick.openDetails(drink)
                    }
                }
                is UserDrink -> {
                    binding.cardName.text = drink.name
                    binding.drinkCard.setOnClickListener{
                        onItemClick.openDetails(drink)
                    }
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