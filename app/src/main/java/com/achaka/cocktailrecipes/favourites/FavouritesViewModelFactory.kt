package com.achaka.cocktailrecipes.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import java.lang.IllegalArgumentException

class FavouritesViewModelFactory(
    private val drinkRepository: DrinkRepository,
    private val userDrinkRepository: UserDrinkRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            return FavouritesViewModel(drinkRepository, userDrinkRepository) as T
        }
        else {
            throw IllegalArgumentException()
        }
    }
}
