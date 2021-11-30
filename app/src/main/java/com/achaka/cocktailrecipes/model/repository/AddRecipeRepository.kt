package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.daos.UserDrinksDao
import com.achaka.cocktailrecipes.model.database.entities.DatabaseUserDrink

class AddRecipeRepository(private val database: CocktailsAppDatabase) {

    suspend fun insertUserDrink(drink: DatabaseUserDrink) {
        database.userDrinksDao().insertUserDrink(drink)
    }
}