package com.achaka.cocktailrecipes.model.network.dtos

import com.squareup.moshi.Json

data class CategoryResponse(
    @Json(name = "drinks")
    val response: List<Category>
)

data class Category(
    @Json(name = "strCategory")
    val category: String
)
