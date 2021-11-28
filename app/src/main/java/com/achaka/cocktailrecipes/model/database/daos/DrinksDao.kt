package com.achaka.cocktailrecipes.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.achaka.cocktailrecipes.model.database.DatabaseDrink
import com.achaka.cocktailrecipes.model.domain.Drink
import kotlinx.coroutines.flow.Flow


@Dao
interface DrinksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrink(drink: DatabaseDrink)

    @Query("SELECT * FROM databasedrink WHERE id=:drinkId")
    fun getDrinkById(drinkId: Int): DatabaseDrink

    @Query("SELECT * FROM databasedrink WHERE name LIKE :drinkName")
    fun getDrinksByName(drinkName: String): Flow<List<DatabaseDrink>>

//    @Query("SELECT * FROM databasedrink WHERE name LIKE :ingredientName")
//    fun getDrinkByIngredient(ingredientName: String): Flow<List<String>>

    @Query("SELECT * FROM databasedrink WHERE name=:drinkName")
    fun getDrinksListByDrinkName(drinkName: String): Flow<List<DatabaseDrink>>

}