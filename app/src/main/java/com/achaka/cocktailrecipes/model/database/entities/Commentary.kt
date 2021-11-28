package com.achaka.cocktailrecipes.model.database.entities

import androidx.room.Entity

@Entity
data class Commentary(
    val drinkId: Int,
    val commentary: String
)