package com.achaka.cocktailrecipes.domain.model

import android.os.Parcel
import android.os.Parcelable

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
    val isUserDrink: Boolean
) : Parcelable, DrinkItem() {
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
        listOf(),
        parcel.readByte() == (1).toByte()
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
        parcel.writeString("")
        parcel.writeByte((1).toByte())

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
}