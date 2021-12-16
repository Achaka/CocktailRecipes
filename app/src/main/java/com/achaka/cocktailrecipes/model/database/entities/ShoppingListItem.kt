package com.achaka.cocktailrecipes.model.database.entities

import androidx.room.Entity
import com.achaka.cocktailrecipes.model.domain.Units

@Entity(tableName = "shopping_lists_table")
data class ShoppingListItem(
    val listId: Int,
    val itemName: String,
    //amount is unit and
    val amount: Pair<String, Units?>
)

