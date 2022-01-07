package com.achaka.cocktailrecipes.domain.model

import com.achaka.cocktailrecipes.data.database.entities.ShoppingListItem

data class IngredientMeasureItem(
    var ingredientName: String,
    var measure: Double?,
    var measureString: String?,
    var range: Pair<Double, Double>?,
    var unit: Pair<Units, String>
)

fun IngredientMeasureItem.asShoppingListItem(): ShoppingListItem {
    return ShoppingListItem(
        0,
        ingredientName,
        getAmount(measure, range, unit)
    )
}

fun getAmount(measure: Double?, range: Pair<Double, Double>?, unit: Pair<Units, String>): Pair<String, Units?> {
    var shoppingMeasure: String = ""
    var shoppinUnits: Units? = null
    if (measure != null) {
        shoppingMeasure = measure.toString()
    } else if (range != null) {
        shoppingMeasure = range.second.toString()
    }
    shoppinUnits = unit.first

    return Pair(shoppingMeasure, shoppinUnits)
}
