package com.achaka.cocktailrecipes.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.model.database.entities.Commentary
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.IngredientMeasureItem
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DrinkDetailsViewModel(private val repository: DrinkRepository): ViewModel() {
    //add to favourites
    fun addToFavourites(drinkItem: DrinkItem?) {
        viewModelScope.launch {
            if (drinkItem != null) {
                withContext(Dispatchers.IO) {
                    repository.addToFavourites(drinkItem)
                }
            }
        }
    }

    fun addCommentary(commentary: Commentary) {

    }
    //count total abv
    fun countTotalAbv(list: List<IngredientMeasureItem>) {

    }
}