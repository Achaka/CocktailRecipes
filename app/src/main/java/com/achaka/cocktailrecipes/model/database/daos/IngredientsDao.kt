package com.achaka.cocktailrecipes.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import com.achaka.cocktailrecipes.model.domain.Ingredient

@Dao
interface IngredientsDao {

    @Insert
    fun insertIngredient(ingredient: Ingredient) {

    }

    fun getIngredient() {

    }

    fun getIngredientsList() {

    }
}