package com.achaka.cocktailrecipes.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.achaka.cocktailrecipes.databinding.SearchRecyclerViewItemBinding
import com.achaka.cocktailrecipes.model.domain.Drink
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class SearchHorizontalAdapter(private val glide: RequestManager) :
    ListAdapter<Drink, SearchHorizontalAdapter.DrinkViewHolder>(SearchDiffUtil()) {

    inner class DrinkViewHolder(val binding: SearchRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink) {
            glide.load(drink.thumbUrl)
                .centerCrop()
                .into(binding.cardImage)
            binding.cardName.text = drink.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding = SearchRecyclerViewItemBinding.inflate(
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