package com.achaka.cocktailrecipes.domain.model

class Ingredient(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val alcoholic: Alcoholic,
    val ABV: String
)