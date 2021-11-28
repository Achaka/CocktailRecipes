package com.achaka.cocktailrecipes.model.domain

import com.achaka.cocktailrecipes.model.database.entities.DatabaseUserIngredient

class UserIngredient(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val alcoholic: Alcoholic,
    val ABV: String,
)

fun UserIngredient.asDatabaseModel(): DatabaseUserIngredient {
    return DatabaseUserIngredient(
        id = id,
        name = name,
        description = description,
        type = type,
        alcoholic = alcoholic.type,
        ABV = ABV
    )
}