package com.achaka.cocktailrecipes.data.network.dtos

import com.achaka.cocktailrecipes.data.database.entities.DatabaseDrink
import com.squareup.moshi.Json

data class FullDrinkResponse(
    @Json(name = "drinks")
    val response: List<FullDrinkDetails>
)

data class FullDrinkDetails(
    @Json(name = "idDrink")
    val id: Int,
    @Json(name = "strDrink")
    val name: String,
    @Json(name = "stDrinkAlternate")
    val alternate: String?,
    @Json(name = "strTags")
    val tags: String?,
    @Json(name = "strVideo")
    val videoUrl: String?,
    @Json(name = "strCategory")
    val category: String?,
    @Json(name = "strIBA")
    val IBA: String?,
    @Json(name = "strAlcoholic")
    val alcoholic: String?,
    @Json(name = "strGlass")
    val glassType: String?,
    @Json(name = "strInstructions")
    val instructions: String?,
    @Json(name = "strInstructionsES")
    val instructionsES: String?,
    @Json(name = "strInstructionsDE")
    val instructionsDE: String?,
    @Json(name = "strInstructionsFR")
    val instructionsFR: String?,
    @Json(name = "strInstructionsIT")
    val instructionsIT: String?,
    @Json(name = "strInstructionsZH-HANS")
    val instructionsZHHANS: String?,
    @Json(name = "strInstructionsZH-HANT")
    val instructionsZHHANT: String?,
    @Json(name = "strDrinkThumb")
    val thumbUrl: String?,
    @Json(name = "strIngredient1")
    val ingredient1: String?,
    @Json(name = "strIngredient2")
    val ingredient2: String?,
    @Json(name = "strIngredient3")
    val ingredient3: String?,
    @Json(name = "strIngredient4")
    val ingredient4: String?,
    @Json(name = "strIngredient5")
    val ingredient5: String?,
    @Json(name = "strIngredient6")
    val ingredient6: String?,
    @Json(name = "strIngredient7")
    val ingredient7: String?,
    @Json(name = "strIngredient8")
    val ingredient8: String?,
    @Json(name = "strIngredient9")
    val ingredient9: String?,
    @Json(name = "strIngredient10")
    val ingredient10: String?,
    @Json(name = "strIngredient11")
    val ingredient11: String?,
    @Json(name = "strIngredient12")
    val ingredient12: String?,
    @Json(name = "strIngredient13")
    val ingredient13: String?,
    @Json(name = "strIngredient14")
    val ingredient14: String?,
    @Json(name = "strIngredient15")
    val ingredient15: String?,
    @Json(name = "strMeasure1")
    val measure1: String?,
    @Json(name = "strMeasure2")
    val measure2: String?,
    @Json(name = "strMeasure3")
    val measure3: String?,
    @Json(name = "strMeasure4")
    val measure4: String?,
    @Json(name = "strMeasure5")
    val measure5: String?,
    @Json(name = "strMeasure6")
    val measure6: String?,
    @Json(name = "strMeasure7")
    val measure7: String?,
    @Json(name = "strMeasure8")
    val measure8: String?,
    @Json(name = "strMeasure9")
    val measure9: String?,
    @Json(name = "strMeasure10")
    val measure10: String?,
    @Json(name = "strMeasure11")
    val measure11: String?,
    @Json(name = "strMeasure12")
    val measure12: String?,
    @Json(name = "strMeasure13")
    val measure13: String?,
    @Json(name = "strMeasure14")
    val measure14: String?,
    @Json(name = "strMeasure15")
    val measure15: String?,
    @Json(name = "strImageSource")
    val imageUrl: String?,
    @Json(name = "strImageAttribution")
    val imageAttribution: String?,
    @Json(name = "strCreativeCommonsConfirmed")
    val isCreativeCommonsConfirmed: String?,
    @Json(name = "dateModified")
    val dateModified: String?
)

fun FullDrinkDetails.asDatabaseModel(): DatabaseDrink {
    return DatabaseDrink(
        id = id,
        name = name,
        alternate = alternate ?: "",
        tags = tags ?: "",
        videoUrl = videoUrl ?: "",
        category = category ?: "",
        IBA = IBA ?: "",
        alcoholic = alcoholic ?: "",
        glassType = glassType ?: "",
        instructions = instructions ?: "",
        instructionsES = instructionsES ?: "",
        instructionsDE = instructionsDE ?: "",
        instructionsFR = instructionsFR ?: "",
        instructionsIT = instructionsIT ?: "",
        instructionsZHHANS = instructionsZHHANS ?: "",
        instructionsZHHANT = instructionsZHHANT ?: "",
        thumbUrl = thumbUrl ?: "",
        ingredientsList = elementsToList(
            ingredient1,
            ingredient2,
            ingredient3,
            ingredient4,
            ingredient5,
            ingredient6,
            ingredient7,
            ingredient8,
            ingredient9,
            ingredient10,
            ingredient11,
            ingredient12,
            ingredient13,
            ingredient14,
            ingredient15
        ),
        measuresList = elementsToList(
            measure1,
            measure2,
            measure3,
            measure4,
            measure5,
            measure6,
            measure7,
            measure8,
            measure9,
            measure10,
            measure11,
            measure12,
            measure13,
            measure14,
            measure15
        ),
        imageUrl = imageUrl ?: "",
        imageAttribution = imageAttribution ?: "",
        dateModified = dateModified ?: ""
    )
}


fun FullDrinkResponse.asDatabaseModel(): List<DatabaseDrink> {
    return this.response.map {
        it.asDatabaseModel()
    }
}

private fun elementsToList(vararg elements: String?): ArrayList<String> {
    val list = elements.filterNotNull()
    return ArrayList(list.map { it.trim() })
}