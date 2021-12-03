package com.achaka.cocktailrecipes

import androidx.recyclerview.widget.DiffUtil
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink

class MainRecyclerViewDiffUtil: DiffUtil.ItemCallback<DrinkItem>() {
    override fun areItemsTheSame(oldItem: DrinkItem, newItem: DrinkItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DrinkItem, newItem: DrinkItem): Boolean {

        var oldId: Int = 0
        var newID: Int = 0

        var oldIsUser: Boolean = false
        var newIsUser: Boolean = false

        when(oldItem) {
            is Drink -> {
                oldId = oldItem.id
                oldIsUser = false
            }
            is UserDrink -> {
                oldId = oldItem.id
                oldIsUser = true
            }
        }

        when(newItem) {
            is Drink -> {
                newID = newItem.id
                newIsUser = false
            }
            is UserDrink -> {
                newID = newItem.id
                newIsUser = true
            }
        }

        return (oldId == newID) && (oldIsUser == newIsUser)
     }
}