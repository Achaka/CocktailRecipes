package com.achaka.cocktailrecipes

import android.app.Application
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import com.achaka.cocktailrecipes.model.repository.DrinkRepository

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
}