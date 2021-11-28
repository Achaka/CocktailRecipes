package com.achaka.cocktailrecipes.addrecipe

import androidx.lifecycle.ViewModel
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.model.domain.asDatabaseModel

class AddRecipeViewModel: ViewModel() {

    fun insertRecipe(drink: UserDrink) {
        val databaseDrink = drink.asDatabaseModel()

    }

}