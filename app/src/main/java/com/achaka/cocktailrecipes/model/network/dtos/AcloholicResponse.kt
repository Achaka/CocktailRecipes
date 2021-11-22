package com.achaka.cocktailrecipes.model.network.dtos

import com.squareup.moshi.Json

data class AlcoholicListResponse(
    @Json(name = "drinks")
    val response: List<AlcoholicType>
)

data class AlcoholicType(
    @Json(name = "strAlcoholic")
    val type: String
)

