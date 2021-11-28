package com.achaka.cocktailrecipes

import android.app.Application
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.repository.AddRecipeRepository

class CocktailsApp: Application() {

    val database: CocktailsAppDatabase by lazy {
        CocktailsAppDatabase.getDatabase(this)
    }
}