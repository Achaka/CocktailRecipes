package com.achaka.cocktailrecipes.model.domain

import com.achaka.cocktailrecipes.model.database.DatabaseDrink
import com.achaka.cocktailrecipes.model.network.dtos.Glass

class Drink(
    val id: Int,
    val name: String,
//    val alternate: String,
    //list
    val tags: List<String>,
    val videoUrl: String,
    val category: Category,
    val IBA: String,
    val alcoholic: Alcoholic,
    val glassType: GlassType,
    val instructions: String,
    val thumbUrl: String,
    val ingredientMeasurePairs: List<Pair<String, String>>,
    val imageUrl: String,
) {
    fun countTotalABV() {

    }
}