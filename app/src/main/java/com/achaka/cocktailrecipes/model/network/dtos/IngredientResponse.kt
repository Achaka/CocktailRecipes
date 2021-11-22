package com.achaka.cocktailrecipes.model.network.dtos

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
