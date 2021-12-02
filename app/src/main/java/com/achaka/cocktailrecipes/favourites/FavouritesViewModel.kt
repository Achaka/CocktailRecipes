package com.achaka.cocktailrecipes.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository

class FavouritesViewModel(
    private val drinkRepository: DrinkRepository,
    private val userDrinkRepository: UserDrinkRepository
) : ViewModel() {

}
