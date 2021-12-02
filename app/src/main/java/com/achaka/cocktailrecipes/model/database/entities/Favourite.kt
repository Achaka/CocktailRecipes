package com.achaka.cocktailrecipes.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class Favourite (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val drinkId: Int,
    val isUserDrink: Boolean
        )
