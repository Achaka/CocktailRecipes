package com.achaka.cocktailrecipes.model.domain

data class IngredientMeasureItem(
    var ingredientName: String,
    var measure: Double?,
    var measureString: String?,
    var range: Pair<Double, Double>?,
    var unit: Pair<Units, String>
)


