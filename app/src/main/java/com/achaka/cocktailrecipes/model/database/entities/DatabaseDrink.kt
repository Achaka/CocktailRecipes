package com.achaka.cocktailrecipes.model.database.entities

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.achaka.cocktailrecipes.model.domain.*
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
    val dateModified: String
)

class StringToListConverter {
    @TypeConverter
    fun stringToListConverter(string: String): ArrayList<String> {
        return ArrayList(string.split(','))
    }

    @TypeConverter
    fun listToStringConverter(list: ArrayList<String>): String {
        return list.joinToString(",")
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
        ingredientMeasureItems = getIngredientMeasureItems(ingredientsList, measuresList),
        isUserDrink = false
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

fun determineUnit(unit: String): Units {
    when (unit) {
        Units.CL.abbrev -> return Units.CL
        Units.OZ.abbrev -> return Units.OZ
        Units.TEASPOON.abbrev -> return Units.TEASPOON
        Units.TABLESPOON.abbrev -> return Units.TABLESPOON
        Units.CUP.abbrev -> return Units.CUP
        Units.SHOT.abbrev -> return Units.SHOT
        Units.GRAM.abbrev -> return Units.GRAM
        Units.L.abbrev -> return Units.L
        Units.ML.abbrev -> return Units.ML
        Units.PART.abbrev -> return Units.PART
        Units.PINT.abbrev -> return Units.PINT
    }
    return Units.NONE
}

fun getIngredientMeasureItems(
    ingredientsList: ArrayList<String>,
    measuresList: ArrayList<String>
): List<IngredientMeasureItem> {
    while (measuresList.size < ingredientsList.size) {
        measuresList.add("")
    }
    Log.d("LIST INGREDIENT BEFORE", ingredientsList.toString())
    Log.d("LIST MEASURES BEFORE", measuresList.toString())
    val items = ArrayList<IngredientMeasureItem>()
    ingredientsList.forEachIndexed { index, ingredientName ->
        val item = IngredientMeasureItem(
            ingredientName,
            parseMeasureNumber(measuresList[index]),
            null,
            parseMeasureRange(measuresList[index]),
            parseUnit(measuresList[index])
        )
        if (item.measure == null) item.measureString = measuresList[index]
        items.add(item)
    }
    Log.d("LIST MEASURES AFTER", items.toString())
    return items
}

fun parseUnit(measureString: String): Pair<Units, String> {
    val regex = Regex("\\d")
    val unitString: StringBuilder = StringBuilder().append("")
    val list = measureString.split(" ")
    list.forEach {
        if (!it.contains(regex))
            return Pair(determineUnit(it.trim().lowercase()), "")
        else {
            //concat the rest of fields to string
            unitString.append("$it ")
        }
    }
    return Pair(Units.NONE, unitString.toString())
}

fun parseMeasureNumber(measure: String): Double? {
    //if string contains number
    val regex = Regex("\\d")
    return if (measure.contains(regex)) {
        if (measure.contains("/")) {
            parseFraction(measure.trim())
        } else parseSimpleNumber(measure.trim())
    } else null
}

fun parseSimpleNumber(measure: String): Double? {
    val regex = Regex("\\d")
    var result: Double? = null
    //determine place of the number
    //format "1 xxx xx xx" "xx 1 xxx" "x xx 1  "
    val dividedString = measure.split(" ").toMutableList()
    dividedString.forEach { item ->
        if (item.contains(regex)) {
            result = try {
                item.toDouble()
            } catch (ex: java.lang.NumberFormatException) {
                null
            }
        }
    }
    return result
}

fun parseFraction(measure: String): Double? {
    //format "xx 1/2 " "xx 1 3/4 xx" "1 3/4 xxx"
    //get the whole part if present, and rational
    val number = Regex("\\d")
    val dividedString = measure.split(" ")
    var whole = 0.0
    var rational = 0.0
    dividedString.forEach {
        if (it.contains(number)) {
            if (it.contains("/")) {
                val fraction = it.trim().split("/")
                rational = try {
                    val numerator = fraction[0].toDouble()
                    val denominator = fraction[1].toDouble()
                    numerator / denominator
                } catch (ex: java.lang.NumberFormatException) {
                    0.0
                }
                //get numerator and denominator
            } else {
                whole = try {
                    it.toDouble()
                } catch (ex: java.lang.NumberFormatException) {
                    0.0
                }
            }
        }
    }
    val result = whole + rational
    return if (result == 0.0) {
        null
    } else result
}

fun parseMeasureRange(measure: String): Pair<Double, Double>? {
    val regex = Regex("\\d")
    var result: Pair<Double, Double>? = null
    if (measure.contains(regex) && measure.contains("-")) {
        val decomposedString = measure.trim().split(" ")
        decomposedString.forEach {
            if (it.contains(regex) && it.contains("-")) {
                val numbers = it.split("-")
                val first = try {
                    numbers[0].toDouble()
                } catch (ex: java.lang.NumberFormatException) {
                    null
                }
                val second = try {
                    numbers[1].toDouble()
                } catch (ex: java.lang.NumberFormatException) {
                    null
                }
                result = if (first == null || second == null)
                    null
                else Pair(first, second)
            }
        }
    }
    return result
}


