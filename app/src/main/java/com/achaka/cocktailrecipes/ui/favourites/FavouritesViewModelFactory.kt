package com.achaka.cocktailrecipes.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class FavouritesViewModelFactory(
    private val drinkRepository: DrinkRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            return FavouritesViewModel(drinkRepository) as T
        }
        else {
            throw IllegalArgumentException()
        }
    }
}
