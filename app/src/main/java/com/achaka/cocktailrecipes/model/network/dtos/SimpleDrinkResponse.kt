package com.achaka.cocktailrecipes.model.network.dtos

import com.squareup.moshi.Json

data class SimpleDrinkResponse(
    @Json(name = "drinks")
    val response: List<SimpleIngredientDetails>
)

data class SimpleIngredientDetails(
    @Json(name = "idDrink")
    val id: Int,
    @Json(name = "strDrink")
    val name: String,
    @Json(name = "strDrinkThumb")
    val thumbUrl: String?
)