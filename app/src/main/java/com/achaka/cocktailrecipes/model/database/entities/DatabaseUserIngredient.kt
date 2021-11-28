package com.achaka.cocktailrecipes.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.achaka.cocktailrecipes.model.database.determineAlcoType
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.domain.UserIngredient

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
