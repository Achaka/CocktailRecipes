package com.achaka.cocktailrecipes.addrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.model.domain.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import kotlinx.coroutines.launch

class AddRecipeViewModel(private val repository: UserDrinkRepository) : ViewModel() {

    fun insertUserDrink(drink: UserDrink) {
        viewModelScope.launch {
           val databaseDrink = drink.asDatabaseModel()
            repository.insertUserDrink(databaseDrink)
        }
    }
}