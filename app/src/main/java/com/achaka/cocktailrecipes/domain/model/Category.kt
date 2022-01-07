package com.achaka.cocktailrecipes.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.achaka.cocktailrecipes.data.database.entities.determineCategory

enum class Category(val category: String) : Parcelable {
    ORDINARY_DRINK("Ordinary Drink"),
    COCKTAIL("Cocktail"),
    MILK_FLOAT_SHAKE("Milk / Float / Shake"),
    OTHER("Other/Unknown"),
    COCOA("Cocoa"),
    SHOT("Shot"),
    COFFEE_TEA("Coffee / Tea"),
    HOMEMADE_LIQUEUR("Homemade Liqueur"),
    PARTY_DRINK("Punch / Party Drink"),
    BEER("Beer"),
    SOFT_DRINK("Soft Drink / Soda");

    constructor(parcel: Parcel) : this(parcel.readString() ?: "Other/Unknown")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return determineCategory(parcel.readString() ?: "Other/Unknown")
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}