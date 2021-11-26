package com.achaka.cocktailrecipes.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.achaka.cocktailrecipes.model.domain.Alcoholic
import com.achaka.cocktailrecipes.model.domain.Category
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.GlassType
import java.lang.StringBuilder

@Entity
data class DatabaseDrink(
    @PrimaryKey
    val id: Int,
    val name: String,
    val alternate: String,
    val tags: String,
    val videoUrl: String,
    val category: String,
    val IBA: String,
    val alcoholic: String,
    val glassType: String,
    val instructions: String,
    val instructionsES: String,
    val instructionsDE: String,
    val instructionsFR: String,
    val instructionsIT: String,
    val instructionsZHHANS: String,
    val instructionsZHHANT: String,
    val thumbUrl: String,
    @TypeConverters(StringToListConverter::class)
    val ingredientsList: ArrayList<String>,
    @TypeConverters(StringToListConverter::class)
    val measuresList: ArrayList<String>,
    val imageUrl: String,
    val imageAttribution: String,
    val dateModified: String,
    val isUserRecipe: Boolean
)

class StringToListConverter {
    @TypeConverter
    fun stringToListConverter(string: String): ArrayList<String> {
        return ArrayList(string.split(','))
    }

    @TypeConverter
    fun listToStringConverter(list: ArrayList<String>): String {
        val sb = StringBuilder()
        list.forEach {
            sb.append(it)
        }
        return sb.toString()
    }
}

fun DatabaseDrink.asDomainModel(): Drink {
    return Drink(
        id = id,
        name = name,
        tags = tags.split(','),
        videoUrl = videoUrl,
        category = determineCategory(category),
        IBA = IBA,
        alcoholic = determineAlcoType(alcoholic),
        glassType = determineGlassType(glassType),
        instructions = instructions,
        thumbUrl = thumbUrl,
        ingredientMeasurePairs = getPair(ingredientsList, measuresList),
        imageUrl = imageUrl
    )
}

fun List<DatabaseDrink>.asDomainModel(): List<Drink> {
    return this.map {
        it.asDomainModel()
    }
}

fun determineAlcoType(type: String): Alcoholic {
    when (type) {
        Alcoholic.ALCOHOLIC.type -> return Alcoholic.ALCOHOLIC
        Alcoholic.NON_ALCOHOLIC.type -> return Alcoholic.NON_ALCOHOLIC
        Alcoholic.OPTIONAL.type -> return Alcoholic.OPTIONAL
    }
    //temporary
    return Alcoholic.OPTIONAL
}

fun determineCategory(category: String): Category {
    when (category) {
        Category.BEER.category -> return Category.BEER
        Category.COCKTAIL.category -> return Category.COCKTAIL
        Category.COCOA.category -> return Category.COCOA
        Category.OTHER.category -> return Category.OTHER
        Category.COFFEE_TEA.category -> return Category.COFFEE_TEA
        Category.HOMEMADE_LIQUEUR.category -> return Category.HOMEMADE_LIQUEUR
        Category.MILK_FLOAT_SHAKE.category -> return Category.MILK_FLOAT_SHAKE
        Category.ORDINARY_DRINK.category -> return Category.ORDINARY_DRINK
        Category.PARTY_DRINK.category -> return Category.PARTY_DRINK
        Category.SHOT.category -> return Category.SHOT
        Category.SOFT_DRINK.category -> return Category.SOFT_DRINK
    }
    //temporary
    return Category.OTHER
}

fun determineGlassType(type: String): GlassType {
    when (type) {
        GlassType.BALLOON.type -> return GlassType.BALLOON
        GlassType.BEER_GLASS.type -> return GlassType.BEER_GLASS
        GlassType.BEER_MUG.type -> return GlassType.BEER_MUG
        GlassType.BEER_PILSNER.type -> return GlassType.BEER_PILSNER
        GlassType.BRANDY_SNIFTER.type -> return GlassType.BRANDY_SNIFTER
        GlassType.CHAMPAGNE_FLUTE.type -> return GlassType.CHAMPAGNE_FLUTE
        GlassType.COCKTAIL.type -> return GlassType.COCKTAIL
        GlassType.COFFEE_MUG.type -> return GlassType.COFFEE_MUG
        GlassType.COLLINS.type -> return GlassType.COPPER_MUG
        GlassType.CORDIAL.type -> return GlassType.CORDIAL
        GlassType.COUPE.type -> return GlassType.COUPE
        GlassType.HIGHBALL.type -> return GlassType.HIGHBALL
        GlassType.HURRICANE.type -> return GlassType.HURRICANE
        GlassType.IRISH_COFFEE_CUP.type -> return GlassType.IRISH_COFFEE_CUP
        GlassType.JAR.type -> return GlassType.JAR
        GlassType.MARGARITA.type -> return GlassType.MARGARITA
        GlassType.MARGARITA_COUPETTE.type -> return GlassType.MARGARITA_COUPETTE
        GlassType.MARTINI.type -> return GlassType.MARTINI
        GlassType.MASON_JAR.type -> return GlassType.MASON_JAR
        GlassType.NICK_AND_NORA.type -> return GlassType.NICK_AND_NORA
        GlassType.OLD_FASHIONED.type -> return GlassType.OLD_FASHIONED
        GlassType.PARFAIT.type -> return GlassType.PARFAIT
        GlassType.PINT_GLASS.type -> return GlassType.PINT_GLASS
        GlassType.PITCHER.type -> return GlassType.PITCHER
        GlassType.POUSSE_CAFE.type -> return GlassType.POUSSE_CAFE
        GlassType.PUNCH_BOWL.type -> return GlassType.PUNCH_BOWL
        GlassType.SHOT.type -> return GlassType.SHOT
        GlassType.WHISKEY.type -> return GlassType.WHISKEY
        GlassType.WHISKEY_SOUR.type -> return GlassType.WHISKEY_SOUR
        GlassType.WHITE_WINE.type -> return GlassType.WHITE_WINE
        GlassType.WINE_GLASS.type -> return GlassType.WHITE_WINE
    }
    //temporary
    return GlassType.HIGHBALL
}

fun getPair(
    ingredientsList: ArrayList<String>,
    measuresList: ArrayList<String>
): List<Pair<String, String>> {
    while (measuresList.size < ingredientsList.size)
        measuresList.add("")

    return ingredientsList.zip(ingredientsList)
}



