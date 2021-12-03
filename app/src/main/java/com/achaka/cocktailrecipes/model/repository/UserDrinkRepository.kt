package com.achaka.cocktailrecipes.model.repository

import androidx.room.Query
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.DatabaseUserDrink
import kotlinx.coroutines.flow.Flow

class UserDrinkRepository(private val database: CocktailsAppDatabase) {

    suspend fun insertUserDrink(drink: DatabaseUserDrink) {
        database.userDrinksDao().insertUserDrink(drink)
    }

    suspend fun getUserDrinksById(drinkId: List<Int>): Flow<List<DatabaseUserDrink>> {
        return database.userDrinksDao().getUserDrinksById(drinkId)
    }

}