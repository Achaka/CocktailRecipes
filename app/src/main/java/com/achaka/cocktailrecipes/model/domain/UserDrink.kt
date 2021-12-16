package com.achaka.cocktailrecipes.model.domain

import com.achaka.cocktailrecipes.model.database.entities.DatabaseUserDrink

class UserDrink(
    val id: Int,
    val name: String,
    val tags: ArrayList<String>?,
    val videoUrl: String,
    val category: Category,
    val IBA: String,
    val alcoholic: Alcoholic,
    val glassType: GlassType,
    val instructions: String,
    val ingredientMeasureItems: List<IngredientMeasureItem>,
    val imageUri: String,
    val isUserDrink: Boolean
) : DrinkItem()

fun UserDrink.asDatabaseModel(): DatabaseUserDrink {
    return DatabaseUserDrink(
        id = id,
        name = name,
        tags = tags ?: ArrayList(),
        videoUrl = videoUrl,
        category = category.category,
        IBA = IBA,
        alcoholic = alcoholic.type,
        glassType = glassType.type,
        instructions = instructions,
        ingredientsList = ingredientMeasureItems.splitIngredientMeasureList().first,
        measuresList = ingredientMeasureItems.splitIngredientMeasureList().second,
        imageUri = imageUri
    )
}

fun listOfPairsToTwoStrings(pairs: List<Pair<String, String>>): Pair<ArrayList<String>, ArrayList<String>> {
    val (list1, list2) = pairs.unzip()
    val arrayList1 = ArrayList<String>(list1)
    val arrayList2 = ArrayList<String>(list2)
    return Pair(arrayList1, arrayList2)
}

fun List<IngredientMeasureItem>.splitIngredientMeasureList(): Pair<ArrayList<String>, ArrayList<String>> {
    val ingredientList = ArrayList<String>()
    val measureList = ArrayList<String>()
    forEach {
        ingredientList.add(it.ingredientName)
        measureList.add(it.measure.toString() + " " + it.unit.first.abbrev)
    }
    return Pair(ingredientList, measureList)
}