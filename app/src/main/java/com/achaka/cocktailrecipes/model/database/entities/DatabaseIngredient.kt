package com.achaka.cocktailrecipes.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.achaka.cocktailrecipes.model.domain.Ingredient

@Entity
data class DatabaseIngredient(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val alcoholic: String,
    val ABV: String
)

fun DatabaseIngredient.asDomainModel(): Ingredient {
    return Ingredient(
        id = id,
        name = name,
        description = description,
        type = type,
        alcoholic = determineAlcoType(alcoholic),
        ABV = ABV
    )
}

fun List<DatabaseIngredient>.asDomainModel(): List<Ingredient> {
    return this.map {
        it.asDomainModel()
    }
}