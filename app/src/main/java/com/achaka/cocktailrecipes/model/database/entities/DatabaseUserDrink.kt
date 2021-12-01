package com.achaka.cocktailrecipes.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.achaka.cocktailrecipes.model.domain.*

@Entity
data class DatabaseUserDrink(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @TypeConverters(StringToListConverter::class)
    val tags: ArrayList<String>,
    val videoUrl: String,
    val category: String,
    val IBA: String,
    val alcoholic: String,
    val glassType: String,
    val instructions: String,
    @TypeConverters(StringToListConverter::class)
    val ingredientsList: ArrayList<String>,
    @TypeConverters(StringToListConverter::class)
    val measuresList: ArrayList<String>,
    //TODO later
    val imageUri: String
)

fun DatabaseUserDrink.asDomainModel(): UserDrink {
    return UserDrink(
        id = id,
        name = name,
        tags = tags,
        videoUrl = videoUrl,
        category = determineCategory(category),
        IBA = IBA,
        alcoholic = determineAlcoType(alcoholic),
        glassType = determineGlassType(glassType),
        instructions = instructions,
        ingredientMeasureList = joinLists(ingredientsList, measuresList),
        imageUri = ""
    )
}

fun joinLists(list1: ArrayList<String>, list2: ArrayList<String>): ArrayList<IngredientMeasureItem> {
    val result = ArrayList<IngredientMeasureItem>()
    list1.forEachIndexed {
            index, s ->
        result.add(IngredientMeasureItem(s, list2[index].toDouble(), determineUnit()))
    }
    return result
}

fun determineUnit(): Units {
    return Units.NONE
}