package com.achaka.cocktailrecipes.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.achaka.cocktailrecipes.data.database.entities.determineGlassType

enum class GlassType(val type: String): Parcelable {

    HIGHBALL("Highball glass"),
    COCKTAIL("Cocktail glass"),
    OLD_FASHIONED("Old-fashioned glass"),
    WHISKEY("Whiskey Glass"),
    COLLINS("Collins glass"),
    POUSSE_CAFE("Pousse cafe glass"),
    CHAMPAGNE_FLUTE("Champagne flute"),
    WHISKEY_SOUR("Whiskey sour glass"),
    CORDIAL("Cordial glass"),
    BRANDY_SNIFTER("Brandy snifter"),
    WHITE_WINE("White wine glass"),
    NICK_AND_NORA("Nick and Nora Glass"),
    HURRICANE("Hurricane glass"),
    COFFEE_MUG("    COFFEE_MUG(Coffee mug"),
    SHOT("Shot glass"),
    JAR("Jar"),
    IRISH_COFFEE_CUP("Irish coffee cup"),
    PUNCH_BOWL("Punch bowl"),
    PITCHER("Pitcher"),
    PINT_GLASS("Pint glass"),
    COPPER_MUG("Copper Mug"),
    WINE_GLASS("Wine Glass"),
    BEER_MUG("Beer mug"),
    MARGARITA_COUPETTE("Margarita/Coupette glass"),
    BEER_PILSNER("Beer pilsner"),
    BEER_GLASS("Beer Glass"),
    PARFAIT("Parfait glass"),
    MASON_JAR("Mason jar"),
    MARGARITA("Margarita glass"),
    MARTINI("Martini Glass"),
    BALLOON("Balloon Glass"),
    COUPE("Coupe Glass");

    constructor(parcel: Parcel) : this(parcel.readString() ?: "HIGHBALL")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GlassType> {
        override fun createFromParcel(parcel: Parcel): GlassType {
            return determineGlassType(parcel.readString() ?: "HIGHBALL")
        }

        override fun newArray(size: Int): Array<GlassType?> {
            return arrayOfNulls(size)
        }
    }
}
