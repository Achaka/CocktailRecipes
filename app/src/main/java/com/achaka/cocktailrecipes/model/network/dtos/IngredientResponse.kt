package com.achaka.cocktailrecipes.model.network.dtos

import com.achaka.cocktailrecipes.model.database.entities.DatabaseIngredient
import com.squareup.moshi.Json

data class IngredientResponse(
    @Json(name = "ingredients")
    val response: List<FullIngredientDetails>
)

data class FullIngredientDetails (
    @Json(name = "idIngredient")
    val id: Int,
    @Json(name = "strIngredient")
    val name: String,
    @Json(name = "strDescription")
    val description: String?,
    @Json(name = "strType")
    val type: String?,
    @Json(name = "strAlcohol")
    val alcoholic: String?,
    @Json(name = "strABV")
    val ABV: String?
)

fun FullIngredientDetails.asDatabaseModel(): DatabaseIngredient {
    return DatabaseIngredient(
        id = id,
        name = name,
        description = description ?: "",
        type = type ?: "",
        alcoholic = alcoholic ?: "",
        ABV = ABV ?: ""
    )
}

fun IngredientResponse.asDatabaseModel(): List<DatabaseIngredient> {
    return this.response.map {
        it.asDatabaseModel()
    }
}