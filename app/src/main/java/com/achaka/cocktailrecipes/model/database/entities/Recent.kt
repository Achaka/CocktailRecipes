package com.achaka.cocktailrecipes.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_table")
data class Recent (
    @PrimaryKey(autoGenerate = true)
    val drinkId: Int,
    val timestamp: Long
        )



