package com.achaka.cocktailrecipes

import android.app.Application
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.IngredientsRepository
import com.achaka.cocktailrecipes.model.repository.SearchRepository

class CocktailsApp: Application() {

    val database: CocktailsAppDatabase by lazy {
        CocktailsAppDatabase.getDatabase(this)
    }

    val userDrinkRepository: UserDrinkRepository by lazy {
        UserDrinkRepository(database)
    }

    val drinkRepository: DrinkRepository by lazy {
        DrinkRepository(database)
    }

    val ingredientsRepository: IngredientsRepository by lazy {
        IngredientsRepository(database)
    }

    val searchRepository: SearchRepository by lazy {
        SearchRepository(database)
    }
}