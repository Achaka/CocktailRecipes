package com.achaka.cocktailrecipes.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commentaries_table")
data class Commentary(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val drinkId: Int,
    val commentary: String,
    val isUserDrink: Boolean
)