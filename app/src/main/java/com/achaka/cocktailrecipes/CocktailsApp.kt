package com.achaka.cocktailrecipes

import android.app.Application
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.repository.AddRecipeRepository
import com.achaka.cocktailrecipes.model.repository.SearchRepository

class CocktailsApp: Application() {

    val database: CocktailsAppDatabase by lazy {
        CocktailsAppDatabase.getDatabase(this)
    }

    val addRecipeRepository: AddRecipeRepository by lazy {
        AddRecipeRepository(database)
    }

    val searchRepository: SearchRepository by lazy {
        SearchRepository(database)
    }
}