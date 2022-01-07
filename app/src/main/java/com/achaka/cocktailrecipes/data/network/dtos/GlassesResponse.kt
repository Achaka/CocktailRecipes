package com.achaka.cocktailrecipes.data.network.dtos

import com.squareup.moshi.Json

data class GlassesResponse(
    @Json(name = "drinks")
    val response: List<Glass>
)

data class Glass(
    @Json(name = "strGlass")
    val type: String
)