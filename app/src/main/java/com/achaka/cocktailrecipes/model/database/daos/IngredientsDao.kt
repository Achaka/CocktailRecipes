package com.achaka.cocktailrecipes.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.achaka.cocktailrecipes.model.domain.Ingredient
import kotlinx.coroutines.flow.Flow


@Dao
interface IngredientsDao {

    @Insert
    fun insertIngredient(ingredient: Ingredient)

    @Query("SELECT * FROM databaseingredient WHERE id=:ingredientId")
    fun getIngredientById(ingredientId: Int): Flow<Ingredient>

    @Query("SELECT * FROM databaseingredient WHERE name=:ingredientName")
    fun getIngredientByName(ingredientName: String): Flow<Ingredient>
}