package com.achaka.cocktailrecipes.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.achaka.cocktailrecipes.data.database.entities.determineAlcoType

enum class Alcoholic(val type: String): Parcelable {

    ALCOHOLIC("Alcoholic"),
    NON_ALCOHOLIC("Non alcoholic"),
    OPTIONAL("Optional alcohol");

    constructor(parcel: Parcel) : this(parcel.readString() ?: "Optional alcohol")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alcoholic> {
        override fun createFromParcel(parcel: Parcel): Alcoholic {
            return determineAlcoType(parcel.readString() ?: "Optional alcohol")
        }

        override fun newArray(size: Int): Array<Alcoholic?> {
            return arrayOfNulls(size)
        }
    }
}
