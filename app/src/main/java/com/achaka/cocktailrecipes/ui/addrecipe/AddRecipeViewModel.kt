package com.achaka.cocktailrecipes.ui.addrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.model.domain.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(private val repository: UserDrinkRepository) :
    ViewModel() {

    fun insertUserDrink(drink: UserDrink) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val databaseDrink = drink.asDatabaseModel()
                repository.insertUserDrink(databaseDrink)
            }
        }
    }
}