package com.achaka.cocktailrecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commentaries_table")
data class Commentary (
    @PrimaryKey
    val drinkId: Int,
    val commentary: String,
    val isUserDrink: Boolean
)