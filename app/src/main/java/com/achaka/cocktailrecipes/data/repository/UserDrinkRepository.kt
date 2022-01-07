package com.achaka.cocktailrecipes.data.repository

import com.achaka.cocktailrecipes.data.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.data.database.entities.DatabaseUserDrink
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDrinkRepository @Inject constructor(private val database: CocktailsAppDatabase) {

    fun insertUserDrink(drink: DatabaseUserDrink) {
        database.userDrinksDao().insertUserDrink(drink)
    }

    fun getUserDrinksById(drinkId: List<Int>): Flow<List<DatabaseUserDrink>> {
        return database.userDrinksDao().getUserDrinksById(drinkId)
    }

}