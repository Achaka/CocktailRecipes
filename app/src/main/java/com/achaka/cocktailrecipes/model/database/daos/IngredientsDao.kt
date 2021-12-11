package com.achaka.cocktailrecipes.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.achaka.cocktailrecipes.model.database.entities.DatabaseIngredient
import kotlinx.coroutines.flow.Flow


@Dao
interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIngredient(ingredient: DatabaseIngredient)

    @Query("SELECT * FROM databaseingredient WHERE id=:ingredientId")
    fun getIngredientById(ingredientId: Int): Flow<DatabaseIngredient>

    @Query("SELECT * FROM databaseingredient WHERE name=:ingredientName")
    fun getIngredientByName(ingredientName: String): Flow<DatabaseIngredient>?
}