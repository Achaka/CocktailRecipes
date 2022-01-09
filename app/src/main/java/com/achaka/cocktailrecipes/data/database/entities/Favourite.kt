package com.achaka.cocktailrecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class Favourite (
    @PrimaryKey
    val drinkId: Int,
    val isUserDrink: Boolean
    )
