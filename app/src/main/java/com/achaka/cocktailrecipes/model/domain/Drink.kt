package com.achaka.cocktailrecipes.model.domain

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

data class Drink(
    val id: Int,
    val name: String,
    val tags: List<String>,
    val videoUrl: String,
    val category: Category,
    val IBA: String,
    val alcoholic: Alcoholic,
    val glassType: GlassType,
    val instructions: String,
    val thumbUrl: String,
//    val ingredientMeasurePairs: List<Pair<String, String>>,
    val ingredientMeasureItems: List<IngredientMeasureItem>,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readString() ?: "",
        parcel.readParcelable<Category>(ClassLoader.getSystemClassLoader()) ?: Category.OTHER,
        parcel.readString() ?: "",
        parcel.readParcelable<Alcoholic>(ClassLoader.getSystemClassLoader()) ?: Alcoholic.OPTIONAL,
        parcel.readParcelable<GlassType>(ClassLoader.getSystemClassLoader()) ?: GlassType.HIGHBALL,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
//        stringToPairs(parcel.readString())
        stringToIngredientMeasureItemList(parcel.readString() ?: ""),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeStringList(tags)
        parcel.writeString(videoUrl)
        parcel.writeParcelable(category, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeString(IBA)
        parcel.writeParcelable(alcoholic, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(glassType, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeString(instructions)
        parcel.writeString(thumbUrl)
//        parcel.writeString(pairsToStrings(ingredientMeasurePairs))
//        parcel.writeString(ingredientMeasureItemListToString(ingredientMeasureItems))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Drink> {
        override fun createFromParcel(parcel: Parcel): Drink {
            return Drink(parcel)
        }

        override fun newArray(size: Int): Array<Drink?> {
            return arrayOfNulls(size)
        }
    }


    fun countTotalABV() {

    }
}

fun ingredientMeasureItemListToString(list: List<IngredientMeasureItem>): String {
    return ""
}

fun stringToIngredientMeasureItemList(string: String): List<IngredientMeasureItem> {
    return listOf()
}

fun pairsToStrings(list: List<Pair<String, String>>): String {
    val al = list.map { it.first + "|" + it.second }
    list.forEach {
        Log.d("pts", it.toString())
    }
    Log.d("pts result", al.joinToString(separator = "/:/"))
    return al.joinToString(separator = "/:/")
}

fun stringToPairs(string: String?): List<Pair<String, String>> {
    val list = string?.split("/:/")
    val pairs = ArrayList<Pair<String, String>>()
    list?.forEach {
        val x = it.split("|")
        val pair = Pair(x[0], x[2])
        pairs.add(pair)
    }
    return pairs
}