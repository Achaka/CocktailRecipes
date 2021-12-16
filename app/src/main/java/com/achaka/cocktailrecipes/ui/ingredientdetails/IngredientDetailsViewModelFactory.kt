package com.achaka.cocktailrecipes.ui.ingredientdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.cocktailrecipes.model.repository.IngredientsRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class IngredientDetailsViewModelFactory(private val ingredientsRepository: IngredientsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientDetailsViewModel::class.java)) {
            return IngredientDetailsViewModel(ingredientsRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
