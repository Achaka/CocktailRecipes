package com.achaka.cocktailrecipes.search

data class QueryParams @JvmOverloads constructor(
    val searchType: SearchType,
    val searchQuery: String = ""
)

enum class SearchType {
    DRINK_BY_DRINK_NAME,
    DRINK_BY_INGREDIENT_NAME
}