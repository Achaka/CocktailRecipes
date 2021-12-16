package com.achaka.cocktailrecipes.ui.addrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class AddRecipeViewModelFactory(private val repository: UserDrinkRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddRecipeViewModel::class.java)) {
            return AddRecipeViewModel(repository) as T
        } else throw IllegalArgumentException()
    }
}