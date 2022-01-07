package com.achaka.cocktailrecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.achaka.cocktailrecipes.domain.model.UserIngredient

@Entity
data class DatabaseUserIngredient (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val alcoholic: String,
    val ABV: String,
        )
fun DatabaseUserIngredient.asDomainModel(): UserIngredient {
    return UserIngredient(
        id = id,
        name = name,
        description = description,
        type = type,
        alcoholic = determineAlcoType(alcoholic),
        ABV = ABV
    )
}
