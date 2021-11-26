package com.achaka.cocktailrecipes.model.network.dtos

import com.squareup.moshi.Json

data class IngredientsListResponse(
    @Json(name = "drinks")
    val response: List<IngredientName>
)

data class IngredientName(
    @Json(name = "strIngredient1")
    val name: String
)
