package com.achaka.cocktailrecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.achaka.cocktailrecipes.domain.model.Alcoholic
import com.achaka.cocktailrecipes.domain.model.Ingredient

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
        alcoholic = determineIngredientAlcoType(alcoholic),
        ABV = ABV
    )
}

fun List<DatabaseIngredient>.asDomainModel(): List<Ingredient> {
    return this.map {
        it.asDomainModel()
    }
}

fun determineIngredientAlcoType(type: String): Alcoholic {
    if (type.trim().lowercase() == "yes") {
        return Alcoholic.ALCOHOLIC
    } else if (type.isEmpty()) {
        return Alcoholic.NON_ALCOHOLIC
    }
    return Alcoholic.NON_ALCOHOLIC
}